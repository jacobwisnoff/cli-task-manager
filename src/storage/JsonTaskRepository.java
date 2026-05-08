package storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonTaskRepository implements TaskRepository {
    private final Map<Integer, Task> store = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final Path filePath;
    private final ObjectMapper mapper;

    public JsonTaskRepository(Path filePath) {
        this.filePath = filePath;
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT);
        load();
    }

    @Override
    public synchronized Task save(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (task.getId() == null) {
            int newId = idGenerator.incrementAndGet();
            task.setId(newId);
            store.put(newId, task);
        } else {
            store.put(task.getId(), task);
            // Keep generator in sync when loading/updating tasks with existing IDs
            idGenerator.updateAndGet(curr -> Math.max(curr, task.getId()));
        }

        writeStoreToFile();
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public synchronized void delete(int id) {
        store.remove(id);
        writeStoreToFile();
    }

    @Override
    public synchronized void saveAll(List<Task> tasks) {
        store.clear();

        if (tasks != null) {
            for (Task task : tasks) {
                if (task == null) {
                    continue;
                }
                if (task.getId() == null) {
                    int newId = idGenerator.incrementAndGet();
                    task.setId(newId);
                } else {
                    idGenerator.updateAndGet(curr -> Math.max(curr, task.getId()));
                }
                store.put(task.getId(), task);
            }
        }

        // Recompute generator from current store to keep it accurate
        int maxId = store.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        idGenerator.set(maxId);

        writeStoreToFile();
    }

    @Override
    public synchronized void load() {
        store.clear();

        try {
            // If file doesn't exist or is empty, there's nothing to load
            if (!Files.exists(filePath) || Files.size(filePath) == 0L) {
                idGenerator.set(0);
                return;
            }

            // If the file contains only whitespace/newlines, treat it as empty
            String content = Files.readString(filePath).trim();
            if (content.isEmpty()) {
                idGenerator.set(0);
                return;
            }

            // Safe to parse
            List<Task> tasks = mapper.readValue(filePath.toFile(), new TypeReference<List<Task>>() {});

            int maxId = 0;
            for (Task task : tasks) {
                if (task == null) continue;

                if (task.getId() == null) {
                    int newId = ++maxId;
                    task.setId(newId);
                    store.put(newId, task);
                } else {
                    store.put(task.getId(), task);
                    maxId = Math.max(maxId, task.getId());
                }
            }

            idGenerator.set(maxId);

            // Optional: rewrite normalized data (addresses partially corrupted files)
            //writeStoreToFile();

        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            // File exists but wasn't valid JSON for the expected shape — treat as "no tasks"
            idGenerator.set(0);
            // Optionally overwrite file with an empty list (uncomment if desired)
            // writeStoreToFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tasks from JSON file: " + filePath, e);
        }
    }

    private synchronized void writeStoreToFile() {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            mapper.writeValue(filePath.toFile(), new ArrayList<>(store.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write tasks to JSON file: " + filePath, e);
        }
    }
}