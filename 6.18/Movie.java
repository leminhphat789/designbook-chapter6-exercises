import java.util.Objects;

/** luu thong tin phim va tao ban sao cua phim */
public record Movie(String title, int minutes) implements Show {
    public Movie {
        Objects.requireNonNull(title);
        if (minutes <= 0) throw new IllegalArgumentException("Invalid duration");
    }
    public String description() { return title + " (" + minutes + " min)"; }
    public Show copy() { return new Movie(title, minutes); }
}
