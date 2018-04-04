package com.sydney.dream.crontab_and_thread;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/2/1.
 */
public class Tool {
    public static void runTask() {
        Map<String, String> demo = new HashMap();
        int i = 1;
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("The " + i + " time  to run the task....");
                i ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runTaskV2() {
        int i = 1;
        while (true) {
            try {
                Thread.sleep(1000);
                if (i == 2 ) {
                    try {
                        throw new Exception("demo");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("The " + i + " time  to run the task....");
                i ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runTaskV3() throws Exception{
        int i = 1;
        while (true) {
            try {
                Thread.sleep(  1000);
                if (i == 2 ) {
                    throw new Exception("demo");
                }
                System.out.println("The " + i + " time  to run the task....");
                i ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
