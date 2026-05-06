package storage;

import model.Task;

import java.util.List;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository{
    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void saveAll(List<Task> tasks) {

    }

    @Override
    public void load() {

    }
}
