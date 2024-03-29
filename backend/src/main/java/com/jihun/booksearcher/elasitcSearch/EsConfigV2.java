package com.jihun.booksearcher.elasitcSearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EsConfigV2 {
    private ElasticsearchClient elasticsearchClient;

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.apikey}")
    private String apikey;

    // 스프링이 이 Bean을 생성할 때 한 번만 호출되는 메서드
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        if (elasticsearchClient == null) {
            RestClient restClient = RestClient.builder(HttpHost.create(url))
                    .setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey " + apikey)})
                    .build();
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
            elasticsearchClient = new ElasticsearchClient(transport);
        }
        return elasticsearchClient;
    }
}
