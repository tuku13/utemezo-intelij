import java.util.Comparator;

public class TaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task t1, Task t2) {
        if(t1.getMaxCpuBurst() == t2.getMaxCpuBurst()){
            Integer i1 = t1.getArrivalTime();
            Integer i2 = t2.getArrivalTime();
            if(i1.equals(i2)){
                Character c1 = t1.getName();
                Character c2 = t2.getName();
                return c1.compareTo(c2);
            }
            return i1.compareTo(i2);
        }
        Integer i1 = t1.getMaxCpuBurst();
        Integer i2 = t2.getMaxCpuBurst();
        return i1.compareTo(i2);
    }
    
}
