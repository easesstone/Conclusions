import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.Cache;

public class IgniteComputeV2 {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("example-ignnite.xml");
        ignite.compute().affinityRun("SQL_PUBLIC_CITY", cityId, new IgniteRunnable() {
            @IgniteInstanceResource
            Ignite ignite;
            @Override
            public void run() {
                // Getting an access to Persons cache.
                IgniteCache<BinaryObject, BinaryObject> people = ignite.cache(
                        "Person").withKeepBinary();
                ScanQuery<BinaryObject, BinaryObject> query =
                        new ScanQuery <BinaryObject, BinaryObject>();
                try (QueryCursor<Cache.Entry<BinaryObject, BinaryObject>> cursor =
                             people.query(query)) {
                    // Iteration over the local cluster node data using the scan query.
                    for (Cache.Entry<BinaryObject, BinaryObject> entry : cursor) {
                        BinaryObject personKey = entry.getKey();
                        // Picking Denver residents only only.
                        if (personKey.<Long>field("CITY_ID") == cityId) {
                            person = entry.getValue();
                            // Sending the warning message to the person.
                        }
                    }
                }
            }
        }
    }
}
