package com.example.CachingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProxyService {
    private final RestTemplate restTemplate= new RestTemplate();

    @Autowired
    private CacheService cacheService;
    WebClient client = WebClient.create();

    public ResponseEntity<String> forwardRequest(String path)
    {

        String cachedResponse = cacheService.getFromCache(path);
        if (cachedResponse != null) {
            cacheService.clearCache();
            return ResponseEntity.ok()
                    .header("X-Source", "Cache")
                    .body(cachedResponse);
        }

        String response = client.get().uri(path).retrieve().bodyToMono(String.class).block();
        cacheService.cacheResponse(path, response);
        return ResponseEntity.ok()
                .header("X-Source", "Server")
                .body(response);
    }


}
