import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

/**
 * 数据网络应用，往分布式缓存里面存储数据
 */
public class IgniteGride {
    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("example-ignnite.xml")) {
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
            // Store keys in cache (values will end up on different cache nodes).
            for (int i = 0; i < 10; i++)
                cache.put(i, Integer.toString(i));
            for (int i = 0; i < 10; i++)
                System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
        }
    }
}
