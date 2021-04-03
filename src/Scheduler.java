public abstract class Scheduler {
    public abstract void add(Task t);
    public abstract int tick(int now);
    public abstract boolean hasTask();
}
