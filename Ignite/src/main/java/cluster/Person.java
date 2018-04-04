package cluster;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Person {
    /** Indexed field. Will be visible for SQL engine. */
    @QuerySqlField(index = true)
    private long id;
    /** Queryable field. Will be visible for SQL engine. */
    @QuerySqlField
    private String name;
    /** Will NOT be visible for SQL engine. */
    private int age;
    /** Indexed field. Will be visible for SQL engine. */
    @QuerySqlField(index = true)
    private Address address;
}