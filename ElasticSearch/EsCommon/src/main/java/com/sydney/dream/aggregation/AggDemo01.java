package com.sydney.dream.aggregation;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

public class AggDemo01 {
    public static void main(String[] args) {
        testDemo01();
    }

    public static void testDemo01() {
        Client client = EsClientInstance.getEsClient();
        System.out.println(client);
        SearchResponse response = client.prepareSearch("cars")
                .setTypes("transactions")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("popular_colors").field("color").size(100000000))
                .get();
//                .execute()
//                .actionGet();
        Terms terms = response.getAggregations().get("popular_colors");
        for(Terms.Bucket bucket : terms.getBuckets()) {
            System.out.println("key: " + bucket.getKeyAsString() + ", doc's count: " + bucket.getDocCount());
        }
    }

}
