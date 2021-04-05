import java.util.ArrayDeque;
import java.util.Queue;

public class RRScheduler extends Scheduler{
    private Queue<Task> tasks;
    private int maxTimeSlice;
    private int timeSlice;
    private Task running;

    public RRScheduler(int timeSlice){
        this.maxTimeSlice = timeSlice;
        this.timeSlice = 0;
        tasks = new ArrayDeque<>();
        running = null;
    }

    @Override
    public void add(Task t) {
        tasks.add(t);
    }

    @Override
    public void tick() {
        if(timeSlice == maxTimeSlice) {
            tasks.add(running);
            running = null;
            timeSlice = 0;
        }

        if(running == null) {
            running = tasks.poll();
        }

        running.tick();

        if(running.isDone()){
            running.setWaitingTime(GlobalScheduler.getInstance().getTime() + 1 - running.getMaxCpuBurst() - running.getArrivalTime() );
            GlobalScheduler.getInstance().addDoneTask(running);
            running = null;
            timeSlice = 0;
        }
        else{
            timeSlice++;
        }
    }

    @Override
    public void interrupt() {
        timeSlice = 0;
        //todo taszk csere
        if(running != null){
            tasks.add(running);
            running = null;
        }
    }

    @Override
    public boolean hasTask() {
        return !tasks.isEmpty() || running != null;
    }
}
