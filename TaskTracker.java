package tasktracker;

public class TaskTracker {

    public static void main(String[] args) {
        // Check if any arguments provided
        if (args.length == 0) {
            printUsage();
            return;
        }

        TaskManager manager = new TaskManager();
        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    handleAdd(args, manager);
                    break;
                case "update":
                    handleUpdate(args, manager);
                    break;
                case "delete":
                    handleDelete(args, manager);
                    break;
                case "mark-in-progress":
                    handleMarkInProgress(args, manager);
                    break;
                case "mark-done":
                    handleMarkDone(args, manager);
                    break;
                case "list":
                    handleList(args, manager);
                    break;
                default:
                    System.out.println(
                        "Error: Unknown command '" + command + "'"
                    );
                    printUsage();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            printUsage();
        }
    }

    // Handle 'add' command
    private static void handleAdd(String[] args, TaskManager manager) {
        if (args.length < 2) {
            System.out.println("Error: Description is required");
            System.out.println(
                "Usage: java TaskTracker add \"Task description\""
            );
            return;
        }

        String description = args[1];
        manager.addTask(description);
    }

    // Handle 'update' command
    private static void handleUpdate(String[] args, TaskManager manager) {
        if (args.length < 3) {
            System.out.println("Error: ID and new description are required");
            System.out.println(
                "Usage: java TaskTracker update <id> \"New description\""
            );
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            String newDescription = args[2];
            manager.updateTask(id, newDescription);
        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Invalid ID. Please provide a valid number."
            );
        }
    }

    // Handle 'delete' command
    private static void handleDelete(String[] args, TaskManager manager) {
        if (args.length < 2) {
            System.out.println("Error: ID is required");
            System.out.println("Usage: java TaskTracker delete <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            manager.deleteTask(id);
        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Invalid ID. Please provide a valid number."
            );
        }
    }

    // Handle 'mark-in-progress' command
    private static void handleMarkInProgress(
        String[] args,
        TaskManager manager
    ) {
        if (args.length < 2) {
            System.out.println("Error: ID is required");
            System.out.println("Usage: java TaskTracker mark-in-progress <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            manager.markInProgress(id);
        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Invalid ID. Please provide a valid number."
            );
        }
    }

    // Handle 'mark-done' command
    private static void handleMarkDone(String[] args, TaskManager manager) {
        if (args.length < 2) {
            System.out.println("Error: ID is required");
            System.out.println("Usage: java TaskTracker mark-done <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            manager.markDone(id);
        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Invalid ID. Please provide a valid number."
            );
        }
    }

    // Handle 'list' command
    private static void handleList(String[] args, TaskManager manager) {
        if (args.length == 1) {
            // List all tasks
            manager.listTasks();
        } else {
            // List by status
            String status = args[1].toLowerCase();

            // Validate status
            if (
                !status.equals(Task.STATUS_TODO) &&
                !status.equals(Task.STATUS_IN_PROGRESS) &&
                !status.equals(Task.STATUS_DONE)
            ) {
                System.out.println("Error: Invalid status '" + status + "'");
                System.out.println("Valid statuses: todo, in-progress, done");
                return;
            }

            manager.listTasksByStatus(status);
        }
    }

    // Print usage instructions
    private static void printUsage() {
        System.out.println("\nUsage: java TaskTracker <command> [arguments]");
        System.out.println("\nCommands:");
        System.out.println(
            "  add \"description\"              - Add a new task"
        );
        System.out.println(
            "  update <id> \"description\"      - Update a task"
        );
        System.out.println("  delete <id>                    - Delete a task");
        System.out.println(
            "  mark-in-progress <id>          - Mark task as in-progress"
        );
        System.out.println(
            "  mark-done <id>                 - Mark task as done"
        );
        System.out.println("  list                           - List all tasks");
        System.out.println(
            "  list <status>                  - List tasks by status (todo/in-progress/done)"
        );
        System.out.println("\nExamples:");
        System.out.println("  java TaskTracker add \"Buy groceries\"");
        System.out.println(
            "  java TaskTracker update 1 \"Buy groceries and cook dinner\""
        );
        System.out.println("  java TaskTracker mark-done 1");
        System.out.println("  java TaskTracker list done");
    }
}
