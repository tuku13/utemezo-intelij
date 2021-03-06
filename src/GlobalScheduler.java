import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalScheduler {
    private List<Scheduler> schedulers;//üzemezők
    private List<Task> futureTasks;
    private List<Task> doneTasks;//Taszk és, hogy mikor fejeződött be
    private static GlobalScheduler globalScheduler;
    private Scheduler lastScheduler;
    private int maxTasks;

    public int getTime() {
        return time;
    }

    private int time;
    private String runningNow;

    private GlobalScheduler(){
        schedulers = new ArrayList<>();
        doneTasks = new ArrayList<>();
        futureTasks = new ArrayList<>();
        this.time = 0;
        this.maxTasks = 0;
        this.runningNow = "";

        schedulers.add(new RRScheduler(2));
        schedulers.add(new SJFScheduler());
    }

    public static GlobalScheduler getInstance(){
        if(globalScheduler == null){
            globalScheduler = new GlobalScheduler();
        }
        return globalScheduler;
    }

    public void add(Task t){
        futureTasks.add(t);
        this.maxTasks++;
    }

    //lefuttatja a teljes folyamatot, majd reseteli a fontos értékeket
    public void run(){
        mainloop : while(doneTasks.size() != maxTasks){
            //System.out.print(time + ") ");
            List<Task> toRemove = new ArrayList<>();
            for(int i = 0; i < futureTasks.size(); i++) {
                Task t = futureTasks.get(i);
                if(t.getArrivalTime() == time) {
                    toRemove.add(t);
                    schedulers.get(t.getPriority()).add(t);
                }
            }
            futureTasks.removeAll(toRemove);

            for(int i = schedulers.size() - 1; i > -1; i--) {
                if(schedulers.get(i).hasTask()){
                    for(int j = i-1; j > -1; j--){
                        schedulers.get(j).interrupt();
                    }
                    schedulers.get(i).tick();
                    lastScheduler = schedulers.get(i);
                    time++;
                    continue mainloop;
                }
            }
            time++;

        }

        listDoneTasks();

        schedulers.clear();
        doneTasks.clear();
        futureTasks.clear();
        maxTasks = 0;
        time = 0;
        runningNow = "";
    }

    //kiírja az összes taszk adatát
    private void listDoneTasks() {
        System.out.println(runningNow);
        StringBuilder stringBuilder = new StringBuilder();

        Collections.sort(doneTasks, new TaskArrivalTimeComparator());

        for(int i = 0; i < doneTasks.size(); i++) {
            Task t = doneTasks.get(i);
            stringBuilder.append(t.getName() + ":" + t.getWaitingTime());
            if(i != doneTasks.size() - 1){
                stringBuilder.append(',');
            }
        }

        System.out.println(stringBuilder.toString());
    }


    public void addDoneTask(Task t) {
        doneTasks.add(t);
    }

    public void addToRunning(char c) { 
        if(runningNow.length() == 0 || runningNow.charAt(runningNow.length() - 1) != c){
            runningNow += c;
        }
    }
}
