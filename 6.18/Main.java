/** chay thu va kiem tra bai 6.18 */
public class Main {
    private static final Movie A = new Movie("Inception", 148);
    private static final Movie B = new Movie("Interstellar", 169);
    private static int checks;
    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }
    private static void state(Schedule schedule, Show monday, Show tuesday, String label) {
        for (Day day : Day.values()) {
            Show expected = day == Day.MONDAY ? monday : day == Day.TUESDAY ? tuesday : new NullShow();
            check(schedule.get(day).equals(expected), label + ": " + day);
        }
        System.out.println(label + " -> Mon: " + monday.description() + "; Tue: " + tuesday.description());
    }

    private static void exercise18() {
        System.out.println("\n6.18 - client uses only Program");
        Program program = new Program();
        check(!program.undoLast(), "New program has no history");
        program.add(A, Day.MONDAY);
        program.add(B, Day.MONDAY);
        check(program.get(Day.MONDAY).equals(B), "Overwrite applied");
        program.undoLast();
        check(program.get(Day.MONDAY).equals(A), "Undo overwrite restores old movie");
        program.redoLast();
        check(program.get(Day.MONDAY).equals(B), "Redo overwrite");
        program.remove(Day.MONDAY);
        check(program.get(Day.MONDAY) instanceof NullShow, "Remove uses NullShow");
        program.undoLast();
        check(program.get(Day.MONDAY).equals(B), "Undo remove");
        program.add(A, Day.SUNDAY);
        program.clear();
        for (Day day : Day.values()) check(program.get(day) instanceof NullShow, "Clear " + day);
        program.undoLast();
        check(program.get(Day.MONDAY).equals(B), "Undo clear restores Monday");
        check(program.get(Day.SUNDAY).equals(A), "Undo clear restores Sunday");
        Show copy1 = program.get(Day.MONDAY), copy2 = program.get(Day.MONDAY);
        check(copy1 != copy2 && copy1.equals(copy2), "get returns independent copies");
        Show null1 = program.get(Day.TUESDAY), null2 = program.get(Day.TUESDAY);
        check(null1 != null2 && null1 instanceof NullShow, "NullShow is copied too");
        program.redoLast();
        for (Day day : Day.values()) check(program.get(day) instanceof NullShow, "Redo clear " + day);
        System.out.println("Program facade: overwrite, remove, clear, undo, redo and copies OK");
    }

    public static void main(String[] args) {
        exercise18();
        System.out.println("\nPASS: " + checks + " checks" );
    }
}
