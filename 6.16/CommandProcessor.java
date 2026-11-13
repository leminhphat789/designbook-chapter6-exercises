import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/** thuc thi lenh va quan ly lich su thao tac */
public final class CommandProcessor {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    public void execute(Command command) {
        Objects.requireNonNull(command).execute();
        undoStack.push(command);
    }
    public boolean undoLast() {
        if (undoStack.isEmpty()) return false;
        Command command = undoStack.peek();
        command.undo();
        undoStack.pop();
        return true;
    }
}
