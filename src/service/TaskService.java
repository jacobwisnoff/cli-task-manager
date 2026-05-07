package service;

import model.Task;
import model.TaskStatus;
import storage.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    // Create a new task (status defaults to IN_PROGRESS)
    public Task createTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("description cannot be empty");
        }
        Task t = new Task(TaskStatus.IN_PROGRESS, description.trim(), LocalDateTime.now());
        return repo.save(t);
    }

    // List all tasks
    public List<Task> listTasks() {
        return repo.findAll();
    }

    // Find a task by id
    public Optional<Task> getTask(int id) {
        return repo.findById(id);
    }

    // Mark a task complete
    public Optional<Task> markComplete(int id) {
        Optional<Task> maybe = repo.findById(id);
        maybe.ifPresent(task -> {
            task.markComplete();
            repo.save(task);
        });
        return maybe;
    }

    // Delete a task
    public boolean deleteTask(int id) {
        Optional<Task> maybe = repo.findById(id);
        if (maybe.isPresent()) {
            repo.delete(id);
            return true;
        }
        return false;
    }
}