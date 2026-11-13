/** bieu dien ngay khong co buoi dien */
public final class NullShow implements Show {
    public String description() { return "No show"; }
    public Show copy() { return new NullShow(); }
    public boolean equals(Object other) { return other instanceof NullShow; }
    public int hashCode() { return 0; }
}
