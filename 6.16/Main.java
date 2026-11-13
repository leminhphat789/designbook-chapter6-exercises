/** chay thu va kiem tra bai 6.16 */
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

    private static void exercise16() {
        System.out.println("6.16 - processor executes and undoes commands");
        Program schedule = new Program();
        CommandProcessor processor = new CommandProcessor();
        Show empty = new NullShow();
        check(!processor.undoLast(), "Empty history");
        processor.execute(Commands.add(schedule, A, Day.MONDAY));
        state(schedule, A, empty, "add A");
        processor.execute(Commands.add(schedule, B, Day.TUESDAY));
        state(schedule, A, B, "add B");
        processor.execute(Commands.remove(schedule, Day.MONDAY));
        state(schedule, empty, B, "remove A");
        processor.execute(Commands.clear(schedule));
        state(schedule, empty, empty, "clear");
        Show[][] expected = {{empty, B}, {A, B}, {A, empty}, {empty, empty}};
        for (int i = 0; i < expected.length; i++) {
            check(processor.undoLast(), "Undo available");
            state(schedule, expected[i][0], expected[i][1], "undo " + (i + 1));
        }
        check(!processor.undoLast(), "History exhausted");
    }
    public static void main(String[] args) {
        exercise16();
        System.out.println("\nPASS: " + checks + " checks" );
    }
}
