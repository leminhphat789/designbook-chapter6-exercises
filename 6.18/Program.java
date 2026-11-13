/** cung cap thao tac cho nguoi dung va giao viec cho bo xu ly lenh */
public final class Program {
    private final Schedule schedule = new Schedule();
    private final CommandProcessor processor = new CommandProcessor();
    public void add(Show show, Day day) { processor.execute(Commands.add(schedule, show, day)); }
    public void remove(Day day) { processor.execute(Commands.remove(schedule, day)); }
    public void clear() { processor.execute(Commands.clear(schedule)); }
    public Show get(Day day) { return schedule.get(day); }
    public boolean undoLast() { return processor.undoLast(); }
    public boolean redoLast() { return processor.redoLast(); }
}
