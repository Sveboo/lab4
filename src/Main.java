import untils.ElevatorSimulator;
import untils.Executor;
import untils.Request;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the number of floors");
        int numberOfFloors = new Scanner(System.in).nextInt();

        Queue<Request> requests = new LinkedList<>();

        ElevatorSimulator build = new ElevatorSimulator(numberOfFloors, requests);
        Thread buildThread = new Thread(build);
        buildThread.start();

        System.out.println("Enter 'stop' to stop elevations' work");
        Executor executor = new Executor(requests, numberOfFloors, 5000);
        Thread exThread = new Thread(executor);
        exThread.start();

        new Scanner(System.in).next();

        System.out.println("Elevators transport the last passengers and stop working");
        executor.breakMove();
        build.breakMove();

        try {
            buildThread.join();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
