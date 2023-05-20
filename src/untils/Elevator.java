package untils;
import java.util.ArrayList;
import java.util.Queue;

public class Elevator implements Runnable{
    private String numberOfElevator;
    private Direction status;
    private int curFloor;

    private int endFloor;
    private int numberOfFloors;
    private Queue<Request> requests;

    public Elevator(){}

    public Elevator(String numberOfElevator, Direction status, int curFloor, int endFloor, int numberOfFloors, Queue<Request> requests) {
        this.numberOfElevator = numberOfElevator;
        this.status = status;
        this.curFloor = curFloor;
        this.endFloor = endFloor;
        this.numberOfFloors = numberOfFloors;
        this.requests = requests;
    }

    public Direction getStatus() {
        return status;
    }

    public void setStatus(Direction status) {
        this.status = status;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    public int getEndFloor() {
        return endFloor;
    }

    public void setEndFloor(int endFloor) {
        this.endFloor = endFloor;
    }

    @Override
    public void run(){
        System.out.printf("%s elevator has received a request. It's moving %s to the %d floor\n", numberOfElevator, status, endFloor);

        moveTo();
        System.out.printf("%s elevator has reached the %d floor and took a passenger. It's moving %s\n", numberOfElevator, endFloor, status);

        endFloor = status == Direction.UP? numberOfFloors : 0;

        moveTo();
        System.out.printf("%s elevator has ended its work\n", numberOfElevator);

        this.endFloor = -1;
        this.status = Direction.BREAK;
    }

    public void moveTo(){
        try{
            ArrayList<Integer> curRequests = new ArrayList<>();
            int step = curFloor < endFloor ? 1 : -1;

            while(curFloor != endFloor) {

                int initialSize = curRequests.size();
                if (step == 1) {
                    for (Request r : requests) {

                        if ((r.getDirection() == status || endFloor == numberOfFloors
                                && r.getDirection() == Direction.UP)
                                && r.getEndFloor() >= curFloor) {
                            curRequests.add(r.getEndFloor());
                        }

                    }
                } else {
                    for (Request r : requests) {
                        if ((r.getDirection() == status || endFloor == 0
                                && r.getDirection() == Direction.DOWN)
                                && r.getEndFloor() <= curFloor) {
                            curRequests.add(r.getEndFloor());
                        }
                    }
                }

                for (int i = initialSize; i < curRequests.size(); i++){
                    Request curReq = new Request(status, curRequests.get(i));
                    requests.remove(curReq);
                }

                while(curRequests.contains(curFloor)){
                    System.out.printf("%s elevator took a passenger at the %d floor\n", numberOfElevator, curFloor);
                    curRequests.remove(Integer.valueOf(curFloor));
                }

                curFloor += step;
                System.out.printf("%s elevator is moving %s to the %d floor\n", numberOfElevator, status, curFloor);

                Thread.sleep(1500);
            }

            Thread.sleep(1500);

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
