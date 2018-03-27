package com.sydney.dream.java8lambda;

public class Demo {
    public static void main(String[] args) {

    }

    /**
     * 使用lambda 替代Runable 内部匿名类，
     */
    public void lambdaInRunable(){
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Before Java8, too much code for too little to do.");
            }
        }).start();
//        new Thread( ()->System.out.println("Doing in Java8.") ).start();
    }
}
