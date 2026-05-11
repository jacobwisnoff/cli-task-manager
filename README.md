# CLI Task Manager

A lightweight command-line task manager written in Java.  
Features: create, list, complete/cancel, and delete tasks. Tasks are persisted to a JSON file between runs.

Tech: Java 24, picocli for CLI parsing, Jackson for JSON serialization.

---

## Prerequisites
- Java 24 (or newer) installed and `java` on PATH:

## Build

This project uses Maven to create a standalone executable JAR containing all dependencies.

Generate the executable JAR with:

```bash
mvn clean package
```

## Run

Start the interactive REPL:

```
java -jar target/cli-task-manager.jar

OR once installed, from any folder:

java -jar /path/to/cli-task-manager.jar
```

## Data file location

By default, tasks are stored in the user's home directory:

- Linux/macOS: `~/.cli-task-manager/tasks.json`
- Windows: `%USERPROFILE%\.cli-task-manager\tasks.json`
