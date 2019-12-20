package com.tan.springcloud2producer.controller;

import com.sun.jna.Native;
import com.tan.springcloud2producer.helper.JnaHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jna")
public class JNAController {

    @RequestMapping("/test")
    public void test(int id) {
        JnaHelper.CLibrary INSTANCE = null;
        if (id==0){
            INSTANCE = (JnaHelper.CLibrary) Native.loadLibrary("C:\\Users\\Administrator\\Desktop\\temp\\jnatest\\0\\MyDLLSolution",
                    JnaHelper.CLibrary.class);

        }else {
            INSTANCE = (JnaHelper.CLibrary) Native.loadLibrary("C:\\Users\\Administrator\\Desktop\\temp\\jnatest\\1\\MyDLLSolution",
                    JnaHelper.CLibrary.class);
        }
//        double d = INSTANCE.Add(1, 7777);
//        System.out.println(d);
    }
}
