package br.com.devcave.jira.config;

import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class FeignClientConfiguration {

    @Value("${jira.url:}")
    private String jiraUrl;

    @Bean
    public Client feignClient() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        return new Client.Default(
                new NaiveSSLSocketFactory(new URI(jiraUrl).getHost()),
                new NoopHostnameVerifier());
    }
}