# java io 常用封装
## BuffredReader
```java
class Demo {
    public void demo () {
        String filePath = "*****";
        File file = new File(filePaht);
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                new java.io.InputStreamReader(new java.io.FileInputStream(file)));
    }    
}
```
