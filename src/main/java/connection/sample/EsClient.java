package connection.sample;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EsClient {
    private static Logger LOGGER = LoggerFactory.getLogger(EsClient.class);
    public static void main(String[] args) {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("192.168.219.196", 9200, "http")
        );

        RestClient restClient = restClientBuilder.build();

        try {
            Response response = restClient.performRequest(new Request("GET", "/"));

            LOGGER.info("{}", EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }

        try {
            restClient.close();
        } catch (IOException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
    }
}
