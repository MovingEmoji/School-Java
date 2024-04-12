//Naoya Iida
import java.util.ArrayList;

public class e2_01_2_3 {
    public static void main(String[] args) {
        TaskList taskList = new TaskList();
        Task t1 = new Task("Study math", 2);
        Task t2 = new Task("Read a book", 4);
        Task t3 = new Task("Walking", 1);
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);
        taskList.displayTasks();
        taskList.removeTask(t2);
        taskList.displayTasks();
    }
}

class Task {
    private String title;
    private int priority;

    public Task(String title, int priority) {
        this.title = title;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }
}
class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public void displayTasks() {
        System.out.println("Tasks:");
        for(Task task : taskList) {
            StringBuilder sb = new StringBuilder();
            sb.append("- ").append(task.getTitle()).append(" (Priority: ").append(task.getPriority()).append(")");
            System.out.println(sb.toString());
        }
    }
}
/*
Tasks:
- Study math (Priority: 2)
- Read a book (Priority: 4)
- Walking (Priority: 1)
Tasks:
- Study math (Priority: 2)
- Walking (Priority: 1)
 */