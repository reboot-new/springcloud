package com.tan.springcloud2producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/get/js")
    public void getUrlFile(HttpServletRequest request, HttpServletResponse response) {

        String url = "alert.js";
        String serverUrl = "C:\\Users\\Administrator\\Desktop\\temp\\";
        String imgUrl = serverUrl + url;
        File file = new File(imgUrl);
//        // 后缀名
//        String suffixName = url.substring(url.lastIndexOf("."));
//        String imgType = "image/" + suffixName;
//        //判断文件是否存在如果不存在就返回默认图标
//        if (!(file.exists() && file.canRead())) {
//            file = new File(request.getSession().getServletContext().getRealPath("/")
//                    + "resource/icons/auth/root.png");
//        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            inputStream.close();
            response.setContentType("text/plain; charset=utf-8");
//            response.setContentType(imgType + ";charset=utf-8");
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
