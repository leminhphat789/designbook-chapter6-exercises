import java.util.EnumMap;

/** chay thu va kiem tra bai 6.17 */
public class Main {
    private static final Movie A = new Movie("Inception", 148);
    private static final Movie B = new Movie("Interstellar", 169);
    private static int checks;
    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }
    private static void state(Program schedule, Show monday, Show tuesday, String label) {
        for (Day day : Day.values()) {
            Show expected = day == Day.MONDAY ? monday : day == Day.TUESDAY ? tuesday : new NullShow();
            check(schedule.get(day).equals(expected), label + ": " + day);
        }
        System.out.println(label + " -> Mon: " + monday.description() + "; Tue: " + tuesday.description());
    }

    private static void exercise17() {
        System.out.println("\n6.17 - processor, undo and redo");
        Program schedule = new Program();
        CommandProcessor processor = new CommandProcessor();
        Show empty = new NullShow();
        check(!processor.undoLast(), "Empty undo");
        check(!processor.redoLast(), "Empty redo");
        processor.execute(Commands.add(schedule, A, Day.MONDAY));
        processor.execute(Commands.add(schedule, B, Day.TUESDAY));
        processor.execute(Commands.remove(schedule, Day.MONDAY));
        processor.execute(Commands.clear(schedule));
        Show[][] undone = {{empty, B}, {A, B}, {A, empty}, {empty, empty}};
        for (int i = 0; i < undone.length; i++) {
            check(processor.undoLast(), "Undo available");
            state(schedule, undone[i][0], undone[i][1], "undo " + (i + 1));
        }
        Show[][] redone = {{A, empty}, {A, B}, {empty, B}, {empty, empty}};
        for (int i = 0; i < redone.length; i++) {
            check(processor.redoLast(), "Redo available");
            state(schedule, redone[i][0], redone[i][1], "redo " + (i + 1));
        }
        check(!processor.redoLast(), "Redo exhausted");
        processor.undoLast();
        processor.execute(Commands.add(schedule, A, Day.FRIDAY));
        check(!processor.redoLast(), "New action must discard redo branch");
        processor.undoLast();
        state(schedule, empty, B, "undo new branch");

        // lenh that bai khong duoc luu vao lich su hoac xoa cac lenh co the lam lai
        processor.undoLast();
        EnumMap<Day, Show> before = schedule.snapshot();
        try {
            processor.execute(new Command() {
                public void execute() { throw new IllegalArgumentException("Expected failure"); }
                public void undo() { throw new AssertionError("Must not enter history"); }
            });
            throw new AssertionError("Expected failure was not thrown");
        } catch (IllegalArgumentException expected) {
            check(schedule.snapshot().equals(before), "Failure preserves schedule");
        }
        check(processor.redoLast(), "Failure preserves redo");
        state(schedule, empty, B, "redo after failure");
    }

    public static void main(String[] args) {
        exercise17();
        System.out.println("\nPASS: " + checks + " checks" );
    }
}
