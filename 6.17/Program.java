import java.util.EnumMap;
import java.util.Objects;

/** quan ly chuong trinh bieu dien */
public final class Program {
    private EnumMap<Day, Show> shows = new EnumMap<>(Day.class);
    Program() { clear(); }
    void add(Show show, Day day) {
        Objects.requireNonNull(day);
        shows.put(day, Objects.requireNonNull(show).copy());
    }
    void remove(Day day) { shows.put(Objects.requireNonNull(day), new NullShow()); }
    void clear() { for (Day day : Day.values()) shows.put(day, new NullShow()); }
    Show get(Day day) { return shows.get(Objects.requireNonNull(day)).copy(); }
    EnumMap<Day, Show> snapshot() { return copyOf(shows); }
    void restore(EnumMap<Day, Show> snapshot) { shows = copyOf(snapshot); }
    private static EnumMap<Day, Show> copyOf(EnumMap<Day, Show> source) {
        EnumMap<Day, Show> result = new EnumMap<>(Day.class);
        source.forEach((day, show) -> result.put(day, show.copy()));
        return result;
    }
}
