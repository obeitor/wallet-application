package com.nimisitech.wallet.integration.config;

import com.nimisitech.wallet.integration.clients.ServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan(basePackages = "com.nimisitech.wallet.integration")
@ConditionalOnProperty(value = "wallet.api.service.integrator", havingValue = "true")
public class WalletIntegrationConfiguration {

    @Value("${wallet.application.url:http://localhost:9090}")
    private String url;

    @Value("${wallet.application.client-id:xyz}")
    private String clientId;

    @Bean
    public ServiceClient serviceClient(){
        HttpComponentsClientHttpRequestFactory simpleClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(60000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        return ServiceClient.builder()
                .baseUrl(url)
                .clientId(clientId)
                .restTemplate(restTemplate)
                .build();
    }
}
