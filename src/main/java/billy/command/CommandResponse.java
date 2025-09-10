package billy.command;

public class CommandResponse {
    private final String message;
    private final boolean shoudlExit;

    public CommandResponse(String message, boolean shoudlExit) {
        this.message = message;
        this.shoudlExit = shoudlExit;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isShoudlExit() {
        return this.shoudlExit;
    }
}
