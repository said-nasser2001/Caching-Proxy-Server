package com.example.CachingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProxyController {
    @Autowired
    private ProxyService proxyService;
    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
    public ResponseEntity<String> proxyGet(@PathVariable String path)
    {
        String originUrl = "https://dummyjson.com";
        return proxyService.forwardRequest(originUrl+"/"+path);
    }
}
