package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private Integer id;
    private TaskStatus status;
    private String description;
    private LocalDateTime createdAt;

    public Task() {

    }

    public Task(TaskStatus status, String description, LocalDateTime createdAt) {
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isComplete() {
        return status == TaskStatus.COMPLETE;
    }

    public void markComplete() {
        this.status = TaskStatus.COMPLETE;
    }

    public void markInProgress() {
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void markCanceled() {
        this.status = TaskStatus.CANCELED;
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
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
