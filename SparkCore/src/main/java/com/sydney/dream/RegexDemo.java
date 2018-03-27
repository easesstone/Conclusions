package com.sydney.dream;

import java.util.regex.Pattern;

public class RegexDemo {
    public static void main(String[] args) {
//        System.out.println(Pattern.matches("[-_A-Za-z0-9\\\\u4e00-\\\\u9fa5]{0,30}ni" +
//                "ma[-_A-Za-z0-9\\\\u4e00-\\\\u9fa5]{0,30}", null));
//        System.out.println(Integer.MAX_VALUE);
        System.out.printf("/user/hive/warehouse/person_table".substring("/user/hive/warehouse/person_table".lastIndexOf("/") + 1));
    }
}
