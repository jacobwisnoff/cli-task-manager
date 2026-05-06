package storage;

import model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task); // on create assign id

    Optional<Task> findById(int id);

    List<Task> findAll();

    void delete(int id);

    void saveAll(List<Task> tasks); // for file backend

    void load(); // optional
}
