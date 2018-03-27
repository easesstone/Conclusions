package com.sydney.dream.crontab_and_thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrontabV3_ScheduledExcutorService {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleAtFixedRate(new ThreadDemo(), 1, 2, TimeUnit.SECONDS);
    }
}
class  ThreadDemo implements Runnable {

    public void run() {
        System.out.println("doing work");
        try {
            throw new Exception("demo exception...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
