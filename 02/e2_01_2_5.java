//Naoya Iida
import java.util.ArrayList;

public class e2_01_2_5 {
    public static void main(String[] args) {

        TaskList taskList = new TaskList();

        Task t1 = new Task("Study math", 2);
        Task t2 = new Task("Read a book", 4);
        Task t3 = new Task("Walking", 1);
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);
        System.out.println(taskList.getHighestPriorityTask());

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

    @Override
    public String toString() {
        return this.title + ":" + this.priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task that = (Task) obj;
        return this.title.equals(that.title);
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
        for(Task forTask : taskList) {
            if(forTask.getTitle().equals(task.getTitle())) {
                taskList.remove(forTask);
            }
        }
    }

    public void displayTasks() {
        System.out.println("Tasks:");
        for(Task task : taskList) {
            StringBuilder sb = new StringBuilder();
            sb.append("- ").append(task.getTitle()).append(" (Priority: ").append(task.getPriority()).append(")");
            System.out.println(sb.toString());
        }
    }

    public Task getHighestPriorityTask() {
        Task highest = null;
        for(Task task : taskList) {
            if(highest == null || highest.getPriority() >= task.getPriority()) {
                highest = task;
            }
        }
        return highest;
    }
}
/*
Tasks:
Walking:1
 */