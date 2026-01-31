### building task-tracker app using Java
source: `https://roadmap.sh/projects/task-tracker`
author: `jqpython`

# Task Tracker CLI

A simple command-line task tracker built with Java to manage your daily tasks.

## Features

- ✅ Add, update, and delete tasks
- ✅ Mark tasks as todo, in-progress, or done
- ✅ List all tasks or filter by status
- ✅ Persistent storage using JSON
- ✅ Simple and intuitive CLI interface

## Requirements

- Java 8 or higher

## Installation

1. Clone the repository:
```bash
git clone https://github.com/YOUR-USERNAME/task-tracker-cli.git
cd task-tracker-cli
```

2. Compile the Java files:
```bash
javac Task.java TaskManager.java TaskTracker.java
```

## Usage

### Add a new task
```bash
java TaskTracker add "Buy groceries"
```

### Update a task
```bash
java TaskTracker update 1 "Buy groceries and cook dinner"
```

### Delete a task
```bash
java TaskTracker delete 1
```

### Mark a task as in-progress
```bash
java TaskTracker mark-in-progress 1
```

### Mark a task as done
```bash
java TaskTracker mark-done 1
```

### List all tasks
```bash
java TaskTracker list
```

### List tasks by status
```bash
java TaskTracker list todo
java TaskTracker list in-progress
java TaskTracker list done
```

## Project Structure
```
task-tracker-cli/
├── Task.java           # Task model class
├── TaskManager.java    # Task management logic
├── TaskTracker.java    # Main CLI application
├── tasks.json          # Data storage (auto-generated)
└── README.md           # This file
```

## How It Works

1. Tasks are stored in a JSON file (`tasks.json`)
2. Each task has: ID, description, status, creation time, and update time
3. The application loads tasks on startup and saves after every change
4. Tasks persist between sessions

## Learning Journey

This project was built as part of my Java learning journey, progressing through:
- Basic Java syntax and I/O
- Object-Oriented Programming
- File handling and JSON parsing
- Command-line argument processing

## Future Enhancements

- [ ] Add task priorities (high, medium, low)
- [ ] Add due dates
- [ ] Add task categories/tags
- [ ] Search functionality
- [ ] Colored terminal output
- [ ] Statistics and analytics

## License

MIT License - Feel free to use this project for learning!

## Acknowledgments

Built from scratch as a learning project. Special thanks to roadmap.sh for the project idea!
