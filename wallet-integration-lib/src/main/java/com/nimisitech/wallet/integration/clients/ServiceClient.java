package com.nimisitech.wallet.integration.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimisitech.wallet.integration.apimodels.envelope.ApiResponseEnvelope;
import com.nimisitech.wallet.integration.exceptions.WalletServiceException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@Getter
@Builder
@Slf4j
public class ServiceClient {
    private RestTemplate restTemplate;
    private String baseUrl;
    private String clientId;

    public Object makeRequest(HttpMethod method, String path, Object request)throws WalletServiceException {
        HttpHeaders headers = this.enrichHeaders(new HttpHeaders());
        HttpEntity<Object> httpEntity = request ==null ? new HttpEntity<>(headers) :
                new HttpEntity<>(request, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.baseUrl);
        URI uri = builder.path(path).build().encode().toUri();
        return getResponse(uri,method,httpEntity);
    }

    private HttpHeaders enrichHeaders(HttpHeaders headers) {
        headers = headers == null ? new HttpHeaders() : headers;
        headers.set("client-id",this.clientId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private Object getResponse(URI uri, HttpMethod method, HttpEntity<?> httpEntity) throws WalletServiceException {
        try{
            ResponseEntity<ApiResponseEnvelope> rsp = this.restTemplate.exchange(uri,method,httpEntity, ApiResponseEnvelope.class);
            final ApiResponseEnvelope response = rsp.getBody();
            log.info(new Gson().toJson(rsp));
            if(response.isSuccess()){
                return response.getResponse();
            }
            throw new WalletServiceException(response.getMessage(),response.getResponseCode());
        }
        catch (WalletServiceException e){
            throw e;
        }
        catch (HttpStatusCodeException e){
            try {
                ApiResponseEnvelope response = new ObjectMapper().readValue(e.getResponseBodyAsString(), ApiResponseEnvelope.class);
                throw new WalletServiceException(response.getMessage(), response.getResponseCode());
            } catch (IOException ex) {
                log.error(ex.getMessage(),ex);
                throw new WalletServiceException("Error from service "+e.getStatusText(),"NA");
            }
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            throw new WalletServiceException("Error reaching service");
        }
    }
}
