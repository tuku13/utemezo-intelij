import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SJFScheduler extends Scheduler{
    List<Task> tasks;

    public SJFScheduler() {
        tasks = new ArrayList<>();
    }

    @Override
    public void add(Task t) {
        tasks.add(t);
    }

    @Override
    public int tick(int now) {
        Task t = getShortesTask();
        tasks.remove(t);

        for(int i = 0; i < t.getMaxCpuBurst(); i++) {
            t.tick();
        }

        t.setWaitingTime(now - t.getArrivalTime());
        GlobalScheduler.getInstance().addDoneTask(t);
        return t.getMaxCpuBurst();
    }

    @Override
    public boolean hasTask() {
        return !tasks.isEmpty();
    }
    
    private Task getShortesTask() {
        return Collections.min(tasks, new TaskComparator());//, new TaskComparator());
    }
}
