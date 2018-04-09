package com.sydney.dream.simple;

public class SimpleDemo {
    public static String demo = "demo";
    static {
        System.out.println("static block.");
        demo = "demo static block.";
    }

    public String demo01 = "demo01";
    {
        System.out.println(demo01);
    }
    public SimpleDemo(){
        System.out.println("constructor.");
    }
}
