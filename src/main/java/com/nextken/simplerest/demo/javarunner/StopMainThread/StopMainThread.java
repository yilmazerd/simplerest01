package com.nextken.simplerest.demo.javarunner.StopMainThread;

import java.util.concurrent.*;

public class StopMainThread {
    public static void mainOld(String[] args) {
        long start = System.currentTimeMillis();
        long end = start + 1*1000; // 1 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ie){
                System.out.println("Thread interrupted");
            }
            System.out.println("Thread complete");
            break;
        }
        System.out.println("Method executed");
    }

    public static void execution() {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            final Future<Object> f = service.submit(() -> {
                // Do you long running calculation here
                Thread.sleep(1337); // Simulate some delay
                return "42";
            });

            System.out.println(f.get(1, TimeUnit.SECONDS));
        } catch (final TimeoutException e) {
            System.err.println("Calculation took to long");
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }

    }

}
