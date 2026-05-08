package app;

import model.Task;
import service.TaskService;
import storage.InMemoryTaskRepository;
import storage.JsonTaskRepository;
import storage.TaskRepository;

import java.nio.file.Path;


public class Main {
    public static void main(String[] args) {
        // wire repository -> service (manual constructor injection)
//        TaskRepository repo = new InMemoryTaskRepository();
        TaskRepository repo = new JsonTaskRepository(Path.of("tasks.json"));
        TaskService service = new TaskService(repo);

        // create tasks via service
        Task t1 = service.createTask("Build CLI Task Manager");
        Task t2 = service.createTask("Write README");
        Task t3 = service.createTask("Work on CLI commands");

        // mark task one complete
        service.markComplete(t1.getId());

        // mark task two canceled
        service.markCanceled(t2.getId());

        // delete task three
        service.deleteTask(t3.getId());

        // list tasks
//        System.out.println("All tasks after creation:");
//        List<Task> tasks = service.listTasks();
//        tasks.forEach(t -> {
//            System.out.println("ID: " + t.getId());
//            System.out.println(t);
//            System.out.println("---");
//        });

//        System.out.println("After marking ID " + t1.getId() + " complete:");
//        service.getTask(t1.getId()).ifPresent(System.out::println);

        // delete second
//        service.deleteTask(t2.getId());
//        System.out.println("After deleting ID " + t2.getId() + ", all tasks:");
//        service.listTasks().forEach(t -> System.out.println(t.getId() + ": " + t.getDescription()));
    }
}