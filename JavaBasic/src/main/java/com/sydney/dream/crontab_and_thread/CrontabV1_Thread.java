package com.sydney.dream.crontab_and_thread;

/**
 *  線程內部報出異常，會結束線程。。。。
 *  想要不結束，把線程內的處理，放到一個方法裡面，把異常在方法裡面處理掉
 *  類似Tool 中的runTaskV2
 */
public class CrontabV1_Thread {
    public static void main(String[] args) {
        new CrontabDemoV1().start();
//        new Thread(new CrontabDemoV2()).start();
    }
}

class CrontabDemoV1 extends Thread {
    @Override
    public void run() {
//        Tool.runTask();
//        Tool.runTaskV2();
        // 以下兩種代碼都會產生異常......
//        try {
//            Tool.runTaskV3();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("doing work...");
//        try {
//            throw new Exception("demo");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}


class CrontabDemoV2 implements Runnable {
    public void run() {

        try {
            Tool.runTaskV2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 以下兩種代碼都會產生異常......
//        try {
//            Tool.runTaskV3();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("doing work...");
//        try {
//            throw new Exception("demo");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

