public class Task{
    private char name;
    private int priority;
    private int arrivalTime;
    private final int maxCpuBurst;
    private int cpuBurst;
    private int waitingTime;

    public Task(char name, int priority, int when, int cpuBurst) {
        this.name = name;
        this.priority = priority;
        this.arrivalTime = when;
        this.maxCpuBurst = cpuBurst;
        this.cpuBurst = cpuBurst;
        this.waitingTime = 0;
    }

    public void tick() {
        cpuBurst--;
        GlobalScheduler.getInstance().addToRunning(name);
        System.out.println(this);
    }

    public boolean isDone() {
        return (cpuBurst == 0);
    }

    public int getMaxCpuBurst() {
        return maxCpuBurst;
    }

    public char getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    
    @Override
    public String toString() {
        return name + ", burst: " + cpuBurst + ", priority: " + this.priority;
    }
}
