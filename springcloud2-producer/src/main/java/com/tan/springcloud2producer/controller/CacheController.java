package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.entity.Student;
import com.tan.springcloud2producer.helper.ImgHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/cache")
public class CacheController {
    /**
     * 强缓存
     * @return
     */
    @RequestMapping("/qiang")
    public ResponseEntity<String> cacheControl() throws Exception{
        String res = ImgHelper.imageToBase64("C:\\Users\\Administrator\\Desktop\\temp\\docker部署管理技术架构图.png");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "public, max-age=31536000");
        return ResponseEntity.status(200).headers(headers).body(res);
    }


    /**
     * 协商缓存
     * @return
     */
    @RequestMapping("/xieshang")
    public ResponseEntity<String> eTag() throws Exception{
        String res = ImgHelper.imageToBase64("C:\\Users\\Administrator\\Desktop\\temp\\docker部署管理技术架构图.png");
        Date tm = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        tm = sdf.parse(sdf.format(tm));
        System.out.println(tm.getTime());

        long ltm = tm.getTime();
        String hex = Long.toHexString(ltm) + "-" + Long.toHexString(res.length());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ETag", hex);
        headers.add("Cache-Control", "public, max-age=0");
        return ResponseEntity.status(200).headers(headers).body(res);
    }

    @RequestMapping("/student")
    public ResponseEntity<Object> getStudent() throws Exception{

        Student st = new Student();
        st.setName("zhang san");
        st.setAge(43);

        Date tm = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        tm = sdf.parse(sdf.format(tm));
        System.out.println(tm.getTime());

        long ltm = tm.getTime();
        String hex = Long.toHexString(ltm) + "-" + Long.toHexString(12);
        HttpHeaders headers = new HttpHeaders();
        headers.add("ETag", hex);
        headers.add("Cache-Control", "public, max-age=0");

        return ResponseEntity.status(200).headers(headers).body(st);

    }
}
