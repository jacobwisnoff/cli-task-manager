package cli;

import model.Task;
import picocli.CommandLine.Command;
import service.TaskService;

import java.util.List;

@Command(name = "list")
public class ListCommand implements Runnable {
    private final TaskService taskService;

    public ListCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run() {
        List<Task> tasks = taskService.listTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }

        System.out.println("Tasks:");
        for (Task task : tasks) {
            System.out.println("\t#" + task.getId() + " - " + task.getDescription() + " (" + task.getStatus() + ")");
        }
    }
}