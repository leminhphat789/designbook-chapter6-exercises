/** chay thu va kiem tra bai 6.15 */
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

    private static void exercise15() {
        System.out.println("\n6.15 - execute and manually undo every command");
        Program schedule = new Program();
        Show empty = new NullShow();
        Command addA = Commands.add(schedule, A, Day.MONDAY);
        Command addB = Commands.add(schedule, B, Day.TUESDAY);
        Command removeA = Commands.remove(schedule, Day.MONDAY);
        Command clear = Commands.clear(schedule);
        addA.execute(); state(schedule, A, empty, "add A");
        addB.execute(); state(schedule, A, B, "add B");
        removeA.execute(); state(schedule, empty, B, "remove A");
        clear.execute(); state(schedule, empty, empty, "clear");
        clear.undo(); state(schedule, empty, B, "undo clear");
        removeA.undo(); state(schedule, A, B, "undo remove");
        addB.undo(); state(schedule, A, empty, "undo add B");
        addA.undo(); state(schedule, empty, empty, "undo add A");
    }

    public static void main(String[] args) {
        exercise15();
        System.out.println("\nPASS: " + checks + " checks" );
    }
}
