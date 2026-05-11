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
    void createTask_increaseTaskCounter() {
        Task t1 = service.createTask("Task 1");
        Task t2 = service.createTask("Task 2");
        Task t3 = service.createTask("Task 3");
        assertEquals(3, t3.getId(), "Should return Id of 3");
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