package com.sydney.dream.crontab_and_thread;

import javafx.concurrent.Task;

import java.util.*;

/**
 * 利用Timer 和TimmerTask 來進行定時任務
 */
public class CrontabV2_Timer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        CrontabDemoV2_TimerV1 task = new CrontabDemoV2_TimerV1();
        timer.schedule(task, new Date(), 1000);
    }
}
class CrontabDemoV2_TimerV1 extends TimerTask {
    public void run() {
        System.out.println("doing work...");
        try {
            throw new Exception("demo Excepthion...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

