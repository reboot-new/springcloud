package com.tan.springcloud2.service;

import feign.Headers;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "SPRINGCLOUD2-PRODUCER",configuration = FeignService.FormSupportConfig.class)
public interface FeignService {

    @PostMapping(value = "/test/post",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    String post(Map<String,?> formParams);

    @RequestMapping(value = "/test/get")
    String get(@RequestParam("left")String left,@RequestParam("right")String right);

    class FormSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;


        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

    }
}
