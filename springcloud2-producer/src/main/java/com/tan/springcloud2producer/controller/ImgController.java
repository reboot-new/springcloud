package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.helper.ImgHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/img")
public class ImgController {

    @RequestMapping("/get")
    public String Get(String path) throws Exception {
        String res = ImgHelper.imageToBase64(path);
        return  res;
    }

    @RequestMapping("/get2")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = new File("G:\\图片\\手机相册\\4s最后一波\\860OKMZO\\IMG_0453.JPG");
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
