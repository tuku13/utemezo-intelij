import java.util.Scanner;

class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        do {
            String name = line.split(",")[0];
            int priority = Integer.parseInt(line.split(",")[1]);
            int when = Integer.parseInt(line.split(",")[2]);
            int cpuBurst = Integer.parseInt(line.split(",")[3]);
            GlobalScheduler.getInstance().add(new Task(name.charAt(0), priority, when, cpuBurst));

            line = scanner.nextLine();
        }
        while(scanner.hasNextLine() && !line.equals(""));
        
        GlobalScheduler.getInstance().run();
        scanner.close();
    }
}