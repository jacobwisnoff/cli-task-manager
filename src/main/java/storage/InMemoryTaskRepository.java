package storage;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> store = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public Task save(Task task) {
        if (task == null) throw new IllegalArgumentException("task cannot be null");

        Integer existingId = task.getId();
        if (existingId == null) {
            Integer newId = idGenerator.incrementAndGet();
            task.setId(newId);
            store.put(newId, task);
        } else {
            store.put(existingId, task);
            // ensure idGenerator is at least existingId so future generated IDs don't collide
            idGenerator.updateAndGet(curr -> Math.max(curr, existingId));
        }
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
    public void delete(int id) {
        store.remove(id);
    }

    @Override
    public void saveAll(List<Task> tasks) {
        if (tasks == null) return;
        for (Task t : tasks) {
            save(t);
        }
    }

    @Override
    public void load() {
        // In-memory implementation will load from disk.
    }
}
