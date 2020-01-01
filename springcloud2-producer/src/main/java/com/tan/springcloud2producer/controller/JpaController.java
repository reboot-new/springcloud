package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.entity.RainFall;
import com.tan.springcloud2producer.repository.RainFallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa")
public class JpaController {
    @Autowired
    private RainFallRepository rainFallRepository;

    @RequestMapping("/byid")
    public RainFall getRainById(){
        return rainFallRepository.findById(6);
    }
}