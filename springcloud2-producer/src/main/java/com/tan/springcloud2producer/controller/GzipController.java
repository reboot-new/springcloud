package com.tan.springcloud2producer.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/gzip")
public class GzipController {

    @RequestMapping("/index")
    public String index(){
        String res =  FileUtil.readString("E:\\Elitel\\03_公司项目\\01_贵州水文项目\\04_BUG日志\\202005\\数据压缩\\reponse.txt", Charset.defaultCharset());

        return res;
    }
}
