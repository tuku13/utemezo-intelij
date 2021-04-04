import java.util.ArrayDeque;
import java.util.Queue;

public class RRScheduler extends Scheduler{
    private Queue<Task> tasks;
    private int maxTimeSlice;
    private int timeSlice;
    private Task running;
    private int last;

    public RRScheduler(int timeSlice){
        this.maxTimeSlice = timeSlice;
        this.timeSlice = 0;
        tasks = new ArrayDeque<Task>();
        running = null;
        this.last = 9999999;
    }

    @Override
    public void add(Task t) {
        tasks.add(t);
    }

    @Override
    public int tick(int now) {
        if(running == null) {
            running = tasks.poll();
        }//ha még nem járt le
        else if(timeSlice != maxTimeSlice && last + 1 != now) {
            tasks.add(running);
            running = null;
            timeSlice = 0;
        }

        last = now;
        running.tick();
        timeSlice++;

        if(running.isDone()){
            running.setWaitingTime(now + 1 - running.getMaxCpuBurst() - running.getArrivalTime() );
            GlobalScheduler.getInstance().addDoneTask(running);
            running = null;
            timeSlice = 0;
        }

        if(timeSlice == maxTimeSlice) {
            tasks.add(running);
            running = null;
            timeSlice = 0;
        }

        return 1;
    }

    @Override
    public boolean hasTask() {
        return !tasks.isEmpty() || running != null;
    }
}
