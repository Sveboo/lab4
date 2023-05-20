package untils;

import java.util.Queue;
import java.util.Random;

public class Executor implements Runnable{
    private final Queue<Request> requests;
    private final int numberOfFloors;

    private final long period;

    private boolean isBreak;

    public void breakMove(){isBreak = true;}

    public Executor(Queue<Request> requests, int numberOfFloors, long period){
        this.requests = requests;
        this.numberOfFloors = numberOfFloors;
        this.period = period;
        this.isBreak = false;
    }

    @Override
    public void run(){
        Random rand = new Random();

        while(!isBreak){
            int floor = rand.nextInt(numberOfFloors);
            Direction direction = rand.nextBoolean()? Direction.DOWN : Direction.UP;
            Request request = new Request(direction, floor);
            requests.add(request);
            System.out.printf("Passenger want to move %s at the %d floor\n", direction, floor);
        
            try{
                Thread.sleep(period);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
