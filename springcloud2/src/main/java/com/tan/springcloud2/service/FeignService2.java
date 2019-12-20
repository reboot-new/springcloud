package com.tan.springcloud2.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SPRINGCLOUD2-PRODUCE2R")
public interface FeignService2 {
}
