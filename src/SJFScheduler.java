import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SJFScheduler extends Scheduler{
    List<Task> tasks;
    Task running;

    public SJFScheduler() {
        tasks = new ArrayList<>();
    }

    @Override
    public void add(Task t) {
        tasks.add(t);
    }

    @Override
    public int tick(int now) {
        if(running == null) {
            running = getShortestTask();
            tasks.remove(running);
        }

        running.tick();

        if(running.isDone()){
            running.setWaitingTime(now + 1 - running.getMaxCpuBurst() - running.getArrivalTime());
            GlobalScheduler.getInstance().addDoneTask(running);
            running = null;
        }
        return 1;
    }

    @Override
    public boolean hasTask() {
        return !tasks.isEmpty() || running != null;
    }
    
    private Task getShortestTask() {
        return Collections.min(tasks, new TaskComparator());//, new TaskComparator());
    }
}
