package cluster;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;

public class ClusterDemo {
    public static void main(String[] args) {
        try(Ignite ignite = Ignition.start("example-ignnite.xml")) {
            IgniteCluster cluster = ignite.cluster();
            System.out.println(cluster);
        }
    }
}
