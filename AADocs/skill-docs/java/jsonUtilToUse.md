1, 加入依赖
```
<dependency>
    <groupId>net.sf.json-lib</groupId>
    <artifactId>json-lib</artifactId>
    <version>2.4</version>
    <classifier>jdk15</classifier>
</dependency>
```

2, 调用JSONObject.formatObject(),转化为JSONObject
```
public class Demo {
    public static void main(String[] args) {
        Map<String, Object> demo = new HashMap<>();
        demo.put("1", "value");
        demo.put("2", 34.5f);
        demo.put("3", 3);
        System.out.println((JSONObject.fromObject(demo).toString()));
        String demoString = "{\"1\":\"value\",\"2\":34.5,\"3\":3}";
        Map<String, Object> demo001 = JSONObject.fromObject(demoString);
        for (Map.Entry<String, Object> entry : demo001.entrySet()) {
            System.out.println(entry.getKey() +  "," + entry.getValue());
        }
    }
}


writer.write(JSONObject.fromObject(map).toString() + "\r\n");

```
