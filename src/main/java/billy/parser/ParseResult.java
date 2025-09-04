package billy.parser;

import java.util.ArrayList;
import billy.task.Task;

public class ParseResult {
    private final ArrayList<Task> taskList;
    private final String message;

    public ParseResult(ArrayList<Task> taskList, String message) {
        this.taskList = taskList;
        this.message = message;
    }

    /** Returns the list of tasks parsed from storage. */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /** Returns the message to display to the user */
    public String getMessage() {
        return message;
    }
}
