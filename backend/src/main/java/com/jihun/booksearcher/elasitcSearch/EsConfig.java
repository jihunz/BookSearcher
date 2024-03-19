package com.jihun.booksearcher.elasitcSearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@Slf4j
@Configuration
public class EsConfig {

    @Value("${elasticsearch.ip}")
    private String ip;
    @Value("${elasticsearch.port}")
    private String port;
    @Value("${elasticsearch.protocol}")
    private String protocol;

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 600000;

    /**
     * 연결
     * @return
     */
    public RestHighLevelClient createConnection() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(ip, parseInt(port), protocol))
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder
                                .setConnectTimeout(CONNECT_TIMEOUT)
                                .setSocketTimeout(SOCKET_TIMEOUT));

        return new RestHighLevelClient(builder);
    }

    /**
     * 해제
     * @param client
     */
    public void closeConnection(RestHighLevelClient client) {
        try {
            if(client != null) {
                client.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}