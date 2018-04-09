package com.sydney.dream.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static Integer getCapacity(ArrayList list) {
        Integer length = null;
        Class c = list.getClass();
        Field f;
        try {
            f = c.getDeclaredField("elementData");
            f.setAccessible(true);
            Object[] o = (Object[]) f.get(list);
            length = o.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  length;
    }
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            list.add("value" + i);
        }
        Integer length = getCapacity(list);
        int size = list.size();
        System.out.println("容量： " + length);
        System.out.println("大小： " + size);
    }
}
