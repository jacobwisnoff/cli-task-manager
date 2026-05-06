package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private TaskStatus status;
    private String description;
    private LocalDateTime createdAt;

    public Task(TaskStatus status, String description, LocalDateTime createdAt) {
//        this.id = id;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public boolean isComplete() {
        return status == TaskStatus.COMPLETE;
    }

    public void markComplete() {
        status = TaskStatus.COMPLETE;
    }

    public void markInProgress() {
        status = TaskStatus.IN_PROGRESS;
    }

    public void markCancelled() {
        status = TaskStatus.CANCELLED;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Status: " + status + "\nDescription: " + description + "\nTask Created: " + createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && status == task.status && Objects.equals(description, task.description) && Objects.equals(createdAt, task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, description, createdAt);
    }
}
