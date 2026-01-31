package tasktracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> tasks;
    private int nextId;
    private static final String TASKS_FILE = "tasks.json";

    // Constructor
    public TaskManager() {
        tasks = new ArrayList<>();
        nextId = 1;
        loadTasks();
    }

    // Add a new task
    public void addTask(String description) {
        Task newTask = new Task(nextId, description);
        tasks.add(newTask);
        nextId++;
        saveTasks();
        System.out.println(
            "Task added successfully (ID: " + newTask.getId() + ")"
        );
    }

    // Update a task's description
    public void updateTask(int id, String newDescription) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setDescription(newDescription);
            saveTasks();
            System.out.println("Task updated successfully (ID: " + id + ")");
        } else {
            System.out.println("Error: Task with ID " + id + " not found");
        }
    }

    // Delete a task
    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            saveTasks();
            System.out.println("Task deleted successfully (ID: " + id + ")");
        } else {
            System.out.println("Error: Task with ID " + id + " not found");
        }
    }

    // Mark task as in-progress
    public void markInProgress(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setStatus(Task.STATUS_IN_PROGRESS);
            saveTasks();
            System.out.println("Task marked as in-progress (ID: " + id + ")");
        } else {
            System.out.println("Error: Task with ID " + id + " not found");
        }
    }

    // Mark task as done
    public void markDone(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setStatus(Task.STATUS_DONE);
            saveTasks();
            System.out.println("Task marked as done (ID: " + id + ")");
        } else {
            System.out.println("Error: Task with ID " + id + " not found");
        }
    }

    // List all tasks
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    // List tasks by status
    public void listTasksByStatus(String status) {
        boolean found = false;

        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks with status '" + status + "' found.");
        }
    }

    // Find task by ID
    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    // Save tasks to JSON file
    private void saveTasks() {
        try (FileWriter writer = new FileWriter(TASKS_FILE)) {
            writer.write("[\n");

            for (int i = 0; i < tasks.size(); i++) {
                writer.write("  " + tasks.get(i).toJson());

                if (i < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load tasks from JSON file
    private void loadTasks() {
        File file = new File(TASKS_FILE);

        if (!file.exists()) {
            return; // No existing tasks, start fresh
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            parseJsonAndLoadTasks(jsonContent.toString());
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    // Parse JSON and create Task objects
    private void parseJsonAndLoadTasks(String json) {
        json = json.trim();
        if (json.startsWith("[")) {
            json = json.substring(1);
        }
        if (json.endsWith("]")) {
            json = json.substring(0, json.length() - 1);
        }

        if (json.trim().isEmpty()) {
            return;
        }

        String[] taskObjects = json.split("\\},\\s*\\{");

        for (String taskJson : taskObjects) {
            taskJson = taskJson.trim();
            if (!taskJson.startsWith("{")) {
                taskJson = "{" + taskJson;
            }
            if (!taskJson.endsWith("}")) {
                taskJson = taskJson + "}";
            }

            Task task = parseTask(taskJson);
            if (task != null) {
                tasks.add(task);

                if (task.getId() >= nextId) {
                    nextId = task.getId() + 1;
                }
            }
        }
    }

    // Parse a single task JSON object
    private Task parseTask(String taskJson) {
        try {
            int id = extractInt(taskJson, "id");
            String description = extractString(taskJson, "description");
            String status = extractString(taskJson, "status");
            String createdAt = extractString(taskJson, "createdAt");
            String updatedAt = extractString(taskJson, "updatedAt");

            return new Task(id, description, status, createdAt, updatedAt);
        } catch (Exception e) {
            System.out.println("Error parsing task: " + e.getMessage());
            return null;
        }
    }

    // Extract integer value from JSON
    private int extractInt(String json, String key) {
        String pattern = "\"" + key + "\":";
        int startIndex = json.indexOf(pattern) + pattern.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }

        String value = json.substring(startIndex, endIndex).trim();
        return Integer.parseInt(value);
    }

    // Extract string value from JSON
    private String extractString(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int startIndex = json.indexOf(pattern) + pattern.length();
        int endIndex = startIndex;

        while (endIndex < json.length()) {
            if (
                json.charAt(endIndex) == '"' &&
                json.charAt(endIndex - 1) != '\\'
            ) {
                break;
            }
            endIndex++;
        }

        String value = json.substring(startIndex, endIndex);

        return value
            .replace("\\\"", "\"")
            .replace("\\\\", "\\")
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t");
    }
}
