package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import service.TaskService;

@Command(
        name = "task",
        description = "CLI Task Manager",
        mixinStandardHelpOptions = true
)
public class TaskManagerApp implements Runnable {

    @Override
    public void run() {
        System.out.println("Use a subcommand such as 'add'.");
    }

    public static CommandLine createCommandLine(TaskService service) {
        CommandLine root = new CommandLine(new TaskManagerApp());

        root.setExecutionExceptionHandler((ex, cmd, parseResult) -> 1);
        //root.setParameterExceptionHandler((ex, args) -> 1);

        root.addSubcommand("add", new AddCommand(service));
        root.addSubcommand("list", new ListCommand(service));
        root.addSubcommand("delete", new DeleteCommand(service));
        root.addSubcommand("complete", new CompleteCommand(service));
        return root;
    }

    public static void printHelp() {
        System.out.println("""
            Commands:
              add <task description>   Add a new task
              list                     Show all tasks
              delete <id>              Delete a task
              complete <id>            Mark a task complete
              help                     Show all commands
              exit                     Quit the app
            """);
    }

    public static void printUnknownCommand(String cmdName) {
        System.out.println("Unknown command: '" + cmdName + "'\n" +
                "Type `help` for list of commands");
    }
}