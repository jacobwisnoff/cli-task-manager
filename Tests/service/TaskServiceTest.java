package service;

import model.Task;
import org.junit.jupiter.api.Test;
import storage.InMemoryTaskRepository;
import storage.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    TaskRepository repo = new InMemoryTaskRepository();
    TaskService service = new TaskService(repo);

    @Test
    void createTask_increaseTaskCount() {
        Task t1 = service.createTask("Task 1");
        assertEquals(1, t1.getId());
    }

    @Test
    void listTasks() {
    }

    @Test
    void getTask() {
    }

    @Test
    void markComplete() {
    }

    @Test
    void deleteTask() {
    }
}