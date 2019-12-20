package com.tan.springcloud2.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/img")
public class ImgController {

    @RequestMapping(value = "/get",produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public BufferedImage getImage() throws IOException {

        BufferedImage buffer = ImageIO.read(new FileInputStream(new File("G:\\图片\\各种素材\\256px\\png\\shadow\\flag_albania.png")));
        return buffer;
    }

}
