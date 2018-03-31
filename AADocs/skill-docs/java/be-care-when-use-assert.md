## java 断言不要乱用
```java
//assert 断言不要乱用
public class Demo {
    public static byte[] inputPicture(String picPath) {
        File imageFile;
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        try {
            imageFile = new File(picPath);
            if (imageFile.exists()) {
                baos = new ByteArrayOutputStream();
                fis = new FileInputStream(imageFile);
                int len;
                while ((len = fis.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert baos != null;
        return baos.toByteArray();
    }
}
//linux 下会继续执行，assert 应该用在测试代码中，而不应该用在正式的代码中
```