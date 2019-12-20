package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.helper.FileHelper;
import com.tan.springcloud2producer.helper.TikaHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/tika")
public class TikaController {
    @RequestMapping("/getContent")
    public String getContent(){

        FileHelper fh  = new FileHelper();
        List<File> fs  = fh.getAllFile(new File("E:\\Elitel\\03_公司项目\\02_黔中水利枢纽\\01_文档资料"));
        for (File f: fs) {
            System.out.println(f.getName());
            TikaHelper.getContextParser(f);
        }
        return fs.size() + "";
    }
}
