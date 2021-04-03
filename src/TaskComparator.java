import java.util.Comparator;

public class TaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task t1, Task t2) {
        if(t1.getMaxCpuBurst() == t2.getMaxCpuBurst()){
            Character c1 = t1.getName();
            Character c2 = t2.getName();
            return c1.compareTo(c2);
        }
        return t1.getMaxCpuBurst() - t2.getMaxCpuBurst();
    }
    
}
