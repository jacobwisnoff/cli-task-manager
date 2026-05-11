package cli;

import model.Task;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import service.TaskService;

import java.util.Optional;

@Command(name = "delete")
public class DeleteCommand implements Runnable {
    private final TaskService taskService;

    @Parameters(paramLabel = "<id>", description = "Task id", arity = "1..*")
    private int id;

    public DeleteCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run() {
        Optional<Task> task = taskService.getTask(id);

        if (task.isEmpty()) {
            System.out.println("Invalid task id!");
            return;
        }

        taskService.deleteTask(id);
        System.out.println("Task deleted!");
    }
}