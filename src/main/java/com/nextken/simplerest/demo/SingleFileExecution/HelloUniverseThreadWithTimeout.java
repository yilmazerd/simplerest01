package com.nextken.simplerest.demo.SingleFileExecution;

import java.util.concurrent.*;

public class HelloUniverseThreadWithTimeout {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Callable, return a future, submit and run the task async
        //executor.submit(() -> { otherTask("sdf"); });
        MainClass mainClass = new MainClass();
        executor.submit(() -> { mainClass.run("sdf"); });
        executor.shutdown(); // Disable new tasks from being submitted
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {

        }

        executor.shutdownNow(); // Disable new tasks from being submitted

        if (!executor.isTerminated()) {
            System.out.println("Your function did not complete in the allowed time : ");
        }

    }

    private static void otherTask(String name) {

        try {
            Thread.sleep(11111);
            System.out.println("I'm other task! " + name);
        } catch (InterruptedException ie) {

        }

        class myClass1 {
            String my;
            String ne;

        };
        class myClass2 {};
    }
}

class MainClass {
    public void run(String name) {

        try {
            Thread.sleep(0);
            System.out.println("I'm other task! " + name);
        } catch (InterruptedException ie) {

        }

        class myClass1 {
            String my;
            String ne;

        };
        class myClass2 {};
    }
}