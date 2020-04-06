package com.tan.springcloud2producer.controller;


//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

//    @Autowired
//    RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public boolean send(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
//        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
//        rabbitTemplate.send("test",map);
//        rabbitTemplate.convertAndSend("test",map);
        return true;
    }
}
