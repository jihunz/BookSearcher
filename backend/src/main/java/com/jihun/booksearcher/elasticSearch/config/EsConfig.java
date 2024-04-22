package com.jihun.booksearcher.elasticSearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Data
public class EsConfig {
    private ElasticsearchClient elasticsearchClient;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.protocol}")
    private String protocol;

    @Value("${elasticsearch.apikey}")
    private String apikey;

    @Value("${elasticsearch.userName}")
    private String userName;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public ElasticsearchClient createClient() {
        if (elasticsearchClient == null) {
            BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
            credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

            RestClient restClient = RestClient.builder(new HttpHost(host, port, protocol))
                    .setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey " + apikey)})
                    .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                            .setDefaultCredentialsProvider(credsProv))
                    .build();
            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

            elasticsearchClient = new ElasticsearchClient(transport);
        }
        return this.elasticsearchClient;
    }
}
