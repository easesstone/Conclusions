package com.sydney.dream.elasticsearch.elasticsink;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.elasticsearch.client.Client;


public class ElasticSearchSink extends AbstractSink implements Configurable{
    private Client client;
    private String hostNames;
    private String indexName;
    private String indexType;
    private String clusterNames;

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }

    public Status process() throws EventDeliveryException {
        return null;
    }

    public void configure(Context context) {

    }

}
