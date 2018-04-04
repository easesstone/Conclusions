import cluster.Person;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * ignite 使用自动发现进行发现服务端
 */
public class IgniteComputeV1 {
    // example-default 中的ip需要是本机的ip
    public static void main(String[] args) {
//        TcpDiscoverySpi spi = new TcpDiscoverySpi();
//        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
//        ipFinder.setMulticastGroup("172.18.18.101");
//        spi.setIpFinder(ipFinder);
//        IgniteConfiguration cfg = new IgniteConfiguration();
//        // Override default discovery SPI.
//        cfg.setDiscoverySpi(spi);
//        cfg.setClientMode(true);
//        // Start Ignite node.
//        Ignition.start(cfg);

//
//        try (Ignite ignite = Ignition.start("example-ignnite.xml")) {
//            IgniteCluster cluster = ignite.cluster();
//            System.out.println("hello");
//            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
//            for (final String word : "Count characters using callable".split(" ")) {
//                calls.add(word::length);
//            }
//            Collection<Integer> res = ignite.compute().call(calls);
//            int sum = res.stream().mapToInt(Integer::intValue).sum();
//            System.out.println("Total number of characters is '" + sum + "'.");
//        }


        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setMulticastGroup("228.10.10.157");
        ipFinder.setAddresses(Arrays.asList("172.18.18.105:47500..47509"));
        spi.setIpFinder(ipFinder);



        IgniteConfiguration cfg = new IgniteConfiguration();
        // Enable client mode.
        cfg.setClientMode(true);

        cfg.setDiscoverySpi(spi);

        // Start Ignite in client mode.
        Ignite ignite = Ignition.start(cfg);

        try (IgniteCache<Long, Person> cache = ignite.cache("personCache")) {

        }

//        Ignition.setClientMode(true);
//        // Start Ignite in client mode.
//        Ignite ignite = Ignition.start();

    }
}
