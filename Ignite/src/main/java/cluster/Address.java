package cluster;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Address {
    /** Indexed field. Will be visible for SQL engine. */
    @QuerySqlField(index = true)
    private String street;
    /** Indexed field. Will be visible for SQL engine. */
    @QuerySqlField(index = true)
    private int zip;
}