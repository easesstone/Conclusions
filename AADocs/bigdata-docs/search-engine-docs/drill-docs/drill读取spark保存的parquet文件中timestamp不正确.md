```
由于sparksql使用parquet文件存储数据时，有个默认参数spark.sql.parquet.int96AsTimestamp为true
即为保持精度，使用96位的int来存储Timestamp类型，这就为使用java代码解析Timestamp类型带来了麻烦 
下面为转换工具类（代码为从github上找的，不是自己写的）。


package com.test.util;  
  
import java.util.concurrent.TimeUnit;  
  
import org.apache.parquet.io.api.Binary;  
  
import com.google.common.primitives.Ints;  
import com.google.common.primitives.Longs;  
  
public class ParquetTimestampUtils {  
      
    /** 
     * julian date的偏移量，2440588相当于1970/1/1 
     */  
    private static final int JULIAN_EPOCH_OFFSET_DAYS = 2440588;  
    private static final long MILLIS_IN_DAY = TimeUnit.DAYS.toMillis(1);  
    private static final long NANOS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1);  
  
    private ParquetTimestampUtils() {}  
  
    /** 
     * Returns GMT timestamp from binary encoded parquet timestamp
      (12 bytes - julian date + time of day nanos). 
     * 
     * @param timestampBinary INT96 parquet timestamp 
     * @return timestamp in millis, GMT timezone 
     */  
    public static long getTimestampMillis(Binary timestampBinary)  
    {  
        if (timestampBinary.length() != 12) {  
            return 0;  
//            throw new PrestoException(HIVE_BAD_DATA, 
//          "Parquet timestamp must be 12 bytes, actual " + timestampBinary.length());  
        }  
        byte[] bytes = timestampBinary.getBytes();  
  
        // little endian encoding - need to invert byte order  
        long timeOfDayNanos = Longs.fromBytes(bytes[7], bytes[6], 
        bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);  
        int julianDay = Ints.fromBytes(bytes[11], bytes[10], bytes[9], bytes[8]);  
  
        return julianDayToMillis(julianDay) + (timeOfDayNanos / NANOS_PER_MILLISECOND);  
    }  
  
    private static long julianDayToMillis(int julianDay)  
    {  
        return (julianDay - JULIAN_EPOCH_OFFSET_DAYS) * MILLIS_IN_DAY;  
    }  
}  


因此如果需要读取spark 生成的parquest 文件，需要对int96AsTimestamp 进行特殊处理，即启用drill 的
store.parquet.reader.int96_as_timestamp 为true

可以在drill 的web 页面上配置。

```

