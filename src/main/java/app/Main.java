package app;

import cli.TaskManagerApp;
import picocli.CommandLine;
import service.TaskService;
import storage.JsonTaskRepository;
import storage.TaskRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path dataDir = Paths.get(System.getProperty("user.home"), ".cli-task-manager");
        Path tasksFile = dataDir.resolve("tasks.json");

        TaskRepository repo = new JsonTaskRepository(tasksFile);
        TaskService service = new TaskService(repo);
        CommandLine commandLine = TaskManagerApp.createCommandLine(service);

        TaskRepl taskRepl = new TaskRepl(commandLine);
        taskRepl.run();
    }
}