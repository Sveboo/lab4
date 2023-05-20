package untils;

import java.util.Queue;

public class ElevatorSimulator implements Runnable{
    private Elevator firstElevator;
    private Elevator secondElevator;
    private Queue<Request> requests;
    private boolean isBreak;

    public ElevatorSimulator(){}

    public ElevatorSimulator(int numberOfFloors, Queue<Request> requests){
        firstElevator = new Elevator("first", Direction.BREAK, 0, -1, numberOfFloors, requests);
        secondElevator = new Elevator("second", Direction.BREAK, 0, -1, numberOfFloors, requests);
        this.requests = requests;
        this.isBreak = false;
    }

    @Override
    public void run(){
        this.isBreak = false;
        Thread firstElevatorThread = new Thread(firstElevator);
        Thread secondElevatorThread = new Thread(secondElevator);

        try{
            while(!isBreak){
                if(requests.isEmpty()){
                    Thread.sleep(500);
                }else if(firstElevator.getStatus() == Direction.BREAK || secondElevator.getStatus() == Direction.BREAK){
                    Thread.sleep(1500);
                    Request request = requests.poll();

                    if(request != null){
                        if(firstElevator.getStatus() == Direction.BREAK && Math.abs(firstElevator.getCurFloor() - request.getEndFloor()) <=
                                Math.abs(firstElevator.getCurFloor() - request.getEndFloor()) || secondElevator.getStatus() != Direction.BREAK){
                                firstElevator.setEndFloor(request.getEndFloor());
                                firstElevator.setStatus(request.getDirection());

                                firstElevatorThread = new Thread(firstElevator);
                                firstElevatorThread.start();
                        }else{
                            secondElevator.setEndFloor(request.getEndFloor());
                            secondElevator.setStatus(request.getDirection());
                            secondElevatorThread = new Thread(secondElevator);
                            secondElevatorThread.start();
                        }
                    }
                }
            }

            firstElevatorThread.join();
            secondElevatorThread.join();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public void breakMove(){
        isBreak = true;
    }
}
