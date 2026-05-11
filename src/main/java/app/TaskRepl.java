package app;

import cli.TaskManagerApp;
import picocli.CommandLine;

import java.util.Scanner;

public class TaskRepl {
    private final CommandLine commandLine;

    public TaskRepl(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public void run() {
        System.out.println("""
                  _____ _      _____   _______        _      __  __                                  \s
                 / ____| |    |_   _| |__   __|      | |    |  \\/  |                                 \s
                | |    | |      | |      | | __ _ ___| | __ | \\  / | __ _ _ __   __ _  __ _  ___ _ __\s
                | |    | |      | |      | |/ _` / __| |/ / | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|
                | |____| |____ _| |_     | | (_| \\__ \\   <  | |  | | (_| | | | | (_| | (_| |  __/ |  \s
                 \\_____|______|_____|    |_|\\__,_|___/_|\\_\\ |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|  \s
                                                                                       __/ |         \s
                                                                                      |___/          \s
""");
        TaskManagerApp.printHelp();
        System.out.println("Try: add Buy milk");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");

                if (!scanner.hasNextLine()) {
                    break;
                }

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye.");
                    break;
                }

                if (input.equalsIgnoreCase("help")) {
                    TaskManagerApp.printHelp();
                    continue;
                }

                String[] commandArgs = input.split("\\s+", 2);
                String cmdName = commandArgs[0];

                if (!commandLine.getSubcommands().containsKey(cmdName)) {
                    TaskManagerApp.printUnknownCommand(cmdName);
                    continue;
                }

                commandLine.execute(commandArgs);
                System.out.flush();

            }
        }
    }

}
