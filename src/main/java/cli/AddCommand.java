package cli;

import model.Task;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import service.TaskService;

@Command(name = "add")
public class AddCommand implements Runnable {
    private final TaskService taskService;

    @Parameters(paramLabel = "<task>", description = "Task description", arity = "1..*")
    private String description;

    public AddCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run() {
        Task task = taskService.createTask(description);
        System.out.println("Created task #" + task.getId() + ": " + task.getDescription());
    }
}