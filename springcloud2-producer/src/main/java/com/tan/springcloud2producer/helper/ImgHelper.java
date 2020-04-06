package com.tan.springcloud2producer.helper;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 图片帮助类，主要功能：图片的格式转化、图片处理等
 * @author tan_alpha
 * @date 2019/5/18 10:59
 */
public class ImgHelper {

    private Logger logger = LoggerFactory.getLogger(ImgHelper.class);

    /**
     * 将图片转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFilePath 图片文件路径
     * @return 输入图片的base64格式
     * @author tan_alpha
     * @date 2019/5/18 17:59
     * <p>
     * 修改记录：修改时间    修改人     修改说明
     * 20200203    牛晨茜     修改在imgFilePath为相对路径下出现异常
     **/
    public static String imageToBase64(String imgFilePath) throws Exception {
        //声明文件输入流
        FileInputStream imgInStream = null;

        try {
            imgInStream = new FileInputStream(imgFilePath);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }finally {
            imgInStream.close();
        }
        return imageToBase64(imgInStream);
    }

    /**
     * 将图片转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFileInputStream 图片文件流
     * @return
     * @author tan_alpha
     * @date 2019/5/18 17:59
     **/
    public static String imageToBase64(FileInputStream imgFileInputStream) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = imgFileInputStream;
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr
     * @param imgFilePath
     * @return
     * @author tan_alpha
     * @date 2019/5/18 18:00
     **/
    public static boolean base64ToImageFile(String imgStr, String imgFilePath) {
        // 图像数据为空
        if (imgStr == null) {return false;}
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String res = imageToBase64("G:\\图片\\手机相册\\4s最后一波\\860OKMZO\\IMG_0566.JPG");
        System.out.println(res);
        base64ToImageFile(res,"C:\\Users\\Administrator\\Desktop\\test\\1.gif");
    }
}