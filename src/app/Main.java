package app;

import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Task> tasks = new HashMap<>();
        tasks.put(1, new Task(
                TaskStatus.IN_PROGRESS,
                "Build CLI Task Manager",
                LocalDateTime.now()));

        System.out.println(tasks.get(1));
        System.out.println(tasks.get(1).isComplete());
        tasks.get(1).markComplete();
        System.out.println(tasks.get(1).isComplete());
    }
}
