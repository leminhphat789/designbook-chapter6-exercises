import java.util.EnumMap;
import java.util.Objects;

/** tao cac lenh them phim, xoa phim va xoa toan bo lich */
public final class Commands {
    private Commands() { }
    static Command add(Schedule target, Show show, Day day) {
        Show savedInput = Objects.requireNonNull(show).copy();
        Objects.requireNonNull(day);
        return changing(target, () -> target.add(savedInput, day));
    }
    static Command remove(Schedule target, Day day) {
        Objects.requireNonNull(day);
        return changing(target, () -> target.remove(day));
    }
    static Command clear(Schedule target) {
        return changing(target, target::clear);
    }

    // tao lenh bang lop vo danh theo yeu cau cua bai 6.14
    // lich chi co bay ngay nen moi lenh luu ban sao cua toan bo lich
    private static Command changing(Schedule target, Runnable action) {
        Objects.requireNonNull(target);
        return new Command() {
            private EnumMap<Day, Show> before;
            private boolean applied;

            public void execute() {
                if (applied) throw new IllegalStateException("Already executed");
                EnumMap<Day, Show> saved = target.snapshot();
                try {
                    action.run();
                } catch (RuntimeException error) {
                    target.restore(saved);
                    throw error;
                }
                before = saved;
                applied = true;
            }
            public void undo() {
                if (!applied) throw new IllegalStateException("Nothing to undo");
                target.restore(before);
                applied = false;
            }
        };
    }
}
