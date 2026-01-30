package tasktracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    // Status constants
    public static final String STATUS_TODO = "todo";
    public static final String STATUS_IN_PROGRESS = "in-progress";
    public static final String STATUS_DONE = "done";

    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    // Constructor for new tasks
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = STATUS_TODO; // Default status
        this.createdAt = getCurrentTimeStamp();
        this.updatedAt = getCurrentTimeStamp();
    }

    // Constructor for loading from JSON
    public Task(
        int id,
        String description,
        String status,
        String createdAt,
        String updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = getCurrentTimeStamp();
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = getCurrentTimeStamp();
    }

    // Get current timestamp
    private String getCurrentTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss"
        );
        return now.format(formatter);
    }

    // Convert to JSON string [will use Jackson library later (manual converting)]
}
