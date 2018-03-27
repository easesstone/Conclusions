package com.hzgc.phoenix_suite;

import com.sydney.dream.HBaseHelper;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PhonenixSuite {
    @Test
    public void testUTF8ToHbase(){
        Table table = HBaseHelper.getTable("objectinfo_test");
        Put put = new Put(Bytes.toBytes("00010001"));
        put.addColumn(Bytes.toBytes("person"), Bytes.toBytes("demotest"), Bytes.toBytes("demo"));
    }

    @Test
    public void demo() {
        List<Float> demo = new ArrayList<Float>();
        demo.add(1f);
        demo.add(2f);
        demo.sort(new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o2, o1);
            }
        });
        System.out.println(demo.get(0));
    }
}
