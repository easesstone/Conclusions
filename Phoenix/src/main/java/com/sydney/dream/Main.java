package com.sydney.dream;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Properties;

public class Main {

    private static float byte2float(byte[] featureByte, int index) {
        int l;
        l = featureByte[index + 0];
        l &= 0xff;
        l |= ((long) featureByte[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) featureByte[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) featureByte[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static float[] string2floatArray(String feature) {
        float[] floatFeature;
        if (null != feature && feature.length() > 0) {
            try {
                byte[] byteFeature = feature.getBytes("ISO-8859-1");
                floatFeature = new float[byteFeature.length / 4];
                byte[] buffer = new byte[4];
                int countByte = 0;
                int countFloat = 0;
                while (countByte < byteFeature.length && countFloat < floatFeature.length) {
                    buffer[0] = byteFeature[countByte];
                    buffer[1] = byteFeature[countByte + 1];
                    buffer[2] = byteFeature[countByte + 2];
                    buffer[3] = byteFeature[countByte + 3];
                    if (countByte % 4 == 0) {
                        floatFeature[countFloat] = byte2float(buffer, 0);
                    }
                    countByte = countByte + 4;
                    countFloat++;
                }
                return floatFeature;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] floatArray2ByteArray(float[] feature) {
        if (feature != null && feature.length == 512) {
            byte[] byteFeature = new byte[feature.length * 4];
            int temp = 0;
            for (float f : feature) {
                byte[] tempbyte = float2byte(f);
                System.arraycopy(tempbyte, 0, byteFeature, temp, tempbyte.length);
                temp = temp + 4;
            }
            return byteFeature;
        }
        return null;
    }

    private static byte[] float2byte(float featureFloat) {
        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(featureFloat);

        byte[] buffer = new byte[4];
        for (int i = 0; i < 4; i++) {
            buffer[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = buffer.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(buffer, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }
        return dest;
    }

    public static String floatArray2string(float[] feature) {
        if (feature != null && feature.length == 512) {
            byte[] bytes = floatArray2ByteArray(feature);
            try {
                return new String(bytes, "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * select instr('123456', '23456')
     * select facecomp("feature","feature") as related from "objectinfo" order by related  limit 100;
     * @param args
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            Properties props = new Properties();
            props.setProperty("phoenix.functions.allowUserDefinedFunctions", "true");
            conn = DriverManager.getConnection("jdbc:phoenix:172.18.18.135:2181", props);
            ResultSet rs;
//        rs = conn.createStatement().executeQuery("select facecomp('feature','feature') as related from \"objectinfo\" limit 100");
//            String sql = " select facecomp(\"feature\",\"feature\") as related from \"objectinfo\" limit 100";
            Table table = HBaseHelper.getTable("objectinfo");
            Get get = new Get(Bytes.toBytes("0001111110105196601274112"));
            Result result = table.get(get);
            byte[] feature = result.getValue(Bytes.toBytes("person"), Bytes.toBytes("feature"));
            String feature_str = Bytes.toString(feature);
            System.out.println(feature_str);
//            String sql = "select facecomp('"+feature_str + "','" + feature_str + "') as related ";
            String sql = "select facecomp(\"feature\", \"feature\") as related from \"objectinfo\" ";
            System.out.println(sql);

            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, feature_str);

            rs = pst.executeQuery(sql);
            System.out.println(rs.next());
            System.out.println(rs.getFloat("related"));
//            System.out.println(rs.next());
//            String feature = rs.getString(1);
//            feature=floatArray2string(string2floatArray(feature));
//            System.out.println(feature.length());
//            System.out.println(conn + "  connected !!!");
//
//            Table table = HBaseHelper.getTable("objectinfo");
//            Get get = new Get(Bytes.toBytes("0001111110105196601274112"));
//            Result result = table.get(get);
//            byte[] feature_01 = result.getValue(Bytes.toBytes("person"), Bytes.toBytes("feature"));
//            String feature_01_str = new String(feature_01, "ISO8859-1");
//
//            Get get1 = new Get(Bytes.toBytes("0001111110105196601274112"));
//            Result result1 = table.get(get);
//            byte[] feature_02 = result.getValue(Bytes.toBytes("person"), Bytes.toBytes("feature"));
//            String feature_02_str = new String(feature_01, "ISO8859-1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
