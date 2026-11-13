/** giao dien thuc thi va hoan tac lenh */
public interface Command {
    void execute();
    void undo();
}
