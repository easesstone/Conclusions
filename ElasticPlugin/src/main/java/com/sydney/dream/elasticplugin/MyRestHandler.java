package com.sydney.dream.elasticplugin;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;

import java.io.IOException;

public class MyRestHandler extends BaseRestHandler {

    public MyRestHandler(Settings settings) {
        super(settings);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        return null;
    }

    @Override
    public boolean canTripCircuitBreaker() {
        return false;
    }

    @Override
    public boolean supportsPlainText() {
        return false;
    }

    @Override
    public boolean supportsContentStream() {
        return false;
    }
}
