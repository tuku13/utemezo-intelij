public abstract class Scheduler {
    public abstract void add(Task t);
    public abstract void tick();
    public abstract void interrupt();
    public abstract boolean hasTask();
}
