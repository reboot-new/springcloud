package com.tan.springcloud2producer.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @author SXD
 * @Date 2018.2.1
 *
 */
public class QRCodeHelper {
    private static int BLACK = 0x000000;
    private static int WHITE = 0xFFFFFF;

    /**
     * 内部类，设置二维码相关参数
     */
    @Data(staticConstructor = "of")
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public  class CodeModel {

        /**
         * 正文
         */
        private String contents;
        /**
         * 二维码宽度
         */
        private int width = 400;
        /**
         * 二维码高度
         */
        private int height = 400;
        /**
         * 图片格式
         */
        private String format = "png";
        /**
         * 编码方式
         */
        private String character_set = "utf-8";
        /**
         * 字体大小
         */
        private int fontSize = 12;
        /**
         * logo
         */
        private File logoFile;
        /**
         * logo所占二维码比例
         */
        private float logoRatio = 0.20f;
        /**
         * 二维码下文字
         */
        private String desc;
        //白边的宽度
        private int whiteWidth;
        //二维码最下边的开始坐标
        private int[] bottomStart;
        //二维码最下边的结束坐标
        private int[] bottomEnd;

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getCharacter_set() {
            return character_set;
        }

        public void setCharacter_set(String character_set) {
            this.character_set = character_set;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public File getLogoFile() {
            return logoFile;
        }

        public void setLogoFile(File logoFile) {
            this.logoFile = logoFile;
        }

        public float getLogoRatio() {
            return logoRatio;
        }

        public void setLogoRatio(float logoRatio) {
            this.logoRatio = logoRatio;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getWhiteWidth() {
            return whiteWidth;
        }

        public void setWhiteWidth(int whiteWidth) {
            this.whiteWidth = whiteWidth;
        }

        public int[] getBottomStart() {
            return bottomStart;
        }

        public void setBottomStart(int[] bottomStart) {
            this.bottomStart = bottomStart;
        }

        public int[] getBottomEnd() {
            return bottomEnd;
        }

        public void setBottomEnd(int[] bottomEnd) {
            this.bottomEnd = bottomEnd;
        }
    }

    /**
     * 1.创建最原始的二维码图片
     * @param info
     * @return
     */
    private BufferedImage createCodeImage(CodeModel info){
        //获取正文
        String contents = StringUtils.isEmpty(info.getContents()) ? "暂无内容" : info.getContents();
        int width = info.getWidth();
        int height = info.getHeight();
        Map<EncodeHintType, Object> hint = new HashMap<>();
        //设置二维码的纠错级别【级别分别为M L H Q ，H纠错能力级别最高，约可纠错30%的数据码字】
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码编码方式【UTF-8】
        hint.put(EncodeHintType.CHARACTER_SET, info.getCharacter_set());
        hint.put(EncodeHintType.MARGIN, 0);
        MultiFormatWriter writer = new MultiFormatWriter();
        BufferedImage img = null;
        try {
            //构建二维码图片
            //QR_CODE 一种矩阵二维码
            BitMatrix bm = writer.encode(contents, BarcodeFormat.QR_CODE, width, height, hint);
            int[] locationTopLeft = bm.getTopLeftOnBit();
            int[] locationBottomRight = bm.getBottomRightOnBit();
            info.setBottomStart(new int[]{locationTopLeft[0], locationBottomRight[1]});
            info.setBottomEnd(locationBottomRight);
            int w = bm.getWidth();
            int h = bm.getHeight();
            img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for(int x=0;x<w;x++){
                for(int y=0;y<h;y++){
                    img.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 2.为二维码增加logo和二维码下文字
     * logo--可以为null
     * 文字--可以为null或者空字符串""
     * @param info
     * @param output
     */
    private void dealLogoAndDesc(CodeModel info, OutputStream output){
        //获取原始二维码图片
        BufferedImage bm = createCodeImage(info);
        //获取Logo图片
        File logoFile = info.getLogoFile();
        int width = bm.getWidth();
        int height = bm.getHeight();
        Graphics g = bm.getGraphics();

        //处理logo
        if(logoFile!=null && logoFile.exists()){
            try{
                BufferedImage logoImg = ImageIO.read(logoFile);
                int logoWidth = logoImg.getWidth();
                int logoHeight = logoImg.getHeight();
                //获取Logo所占二维码比例大小
                float ratio = info.getLogoRatio();
                if(ratio>0){
                    logoWidth = logoWidth>width*ratio ? (int)(width*ratio) : logoWidth;
                    logoHeight = logoHeight>height*ratio ? (int)(height*ratio) : logoHeight;
                }
                int x = (width-logoWidth)/2;
                int y = (height-logoHeight)/2;
                //根据logo 起始位置 和 宽高 在二维码图片上画出logo
                g.drawImage(logoImg, x, y, logoWidth, logoHeight, null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        //处理二维码下文字
        String desc = info.getDesc();
        if(!StringUtils.isEmpty(desc)){
            try{
                //设置文字字体
                int whiteWidth = info.getHeight()-info.getBottomEnd()[1];
                Font font = new Font("黑体", Font.BOLD, info.getFontSize());
                int fontHeight = g.getFontMetrics(font).getHeight();
                //计算需要多少行
                int lineNum = 1;
                int currentLineLen = 0;
                for(int i=0;i<desc.length();i++){
                    char c = desc.charAt(i);
                    int charWidth = g.getFontMetrics(font).charWidth(c);
                    if(currentLineLen+charWidth>width){
                        lineNum++;
                        currentLineLen = 0;
                        continue;
                    }
                    currentLineLen += charWidth;
                }
                int totalFontHeight = fontHeight*lineNum;
                int wordTopMargin = 4;
                BufferedImage bm1 = new BufferedImage(width, height+totalFontHeight+wordTopMargin-whiteWidth, BufferedImage.TYPE_INT_RGB);
                Graphics g1 = bm1.getGraphics();
                if(totalFontHeight+wordTopMargin-whiteWidth>0){
                    g1.setColor(Color.WHITE);
                    g1.fillRect(0, height, width, totalFontHeight+wordTopMargin-whiteWidth);
                }
                g1.setColor(new Color(BLACK));
                g1.setFont(font);
                g1.drawImage(bm, 0, 0, null);
                width = info.getBottomEnd()[0]-info.getBottomStart()[0];
                height = info.getBottomEnd()[1]+1;
                currentLineLen = 0;
                int currentLineIndex = 0;
                int baseLo = g1.getFontMetrics().getAscent();
                for(int i=0;i<desc.length();i++){
                    String c = desc.substring(i, i+1);
                    int charWidth = g.getFontMetrics(font).stringWidth(c);
                    if(currentLineLen+charWidth>width){
                        currentLineIndex++;
                        currentLineLen = 0;
                        g1.drawString(c, currentLineLen + whiteWidth, height+baseLo+fontHeight*(currentLineIndex)+wordTopMargin);
                        currentLineLen = charWidth;
                        continue;
                    }
                    g1.drawString(c, currentLineLen+whiteWidth, height+baseLo+fontHeight*(currentLineIndex) + wordTopMargin);
                    currentLineLen += charWidth;
                }
                g1.dispose();
                bm = bm1;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            ImageIO.write(bm, StringUtils.isEmpty(info.getFormat()) ? info.getFormat() : info.getFormat(), output);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 3.创建 带logo和文字的二维码
     * @param info
     * @param file
     */
    public void createCodeImage(CodeModel info, File file){
        File parent = file.getParentFile();
        if(!parent.exists())parent.mkdirs();
        OutputStream output = null;
        try{
            output = new BufferedOutputStream(new FileOutputStream(file));
            dealLogoAndDesc(info, output);
            output.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.创建  带logo和文字的二维码
     * @param info
     * @param filePath
     */
    public void createCodeImage(CodeModel info, String filePath){
        createCodeImage(info, new File(filePath));
    }

    /**
     * 4.创建  带logo和文字的二维码
     * @param filePath
     */
    public void createCodeImage(String contents,String filePath){
        CodeModel codeModel = new CodeModel();
        codeModel.setContents(contents);
        createCodeImage(codeModel, new File(filePath));
    }


    /**
     * 5.读取 二维码 获取二维码中正文
     * @param input
     * @return
     */
    public String decode(InputStream input){
        Map<DecodeHintType, Object> hint = new HashMap<DecodeHintType, Object>();
        hint.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        String result = "";
        try{
            BufferedImage img = ImageIO.read(input);
            int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
            LuminanceSource source = new RGBLuminanceSource(img.getWidth(), img.getHeight(), pixels);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            Result r = reader.decode(bitmap, hint);
            result = r.getText();
        }catch(Exception e){
            result="读取错误";
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String ss = "你好，中国！";
        System.out.println(ss);
        QRCodeHelper code = new QRCodeHelper();
//        Code.CodeModel codeModel = code.new CodeModel();
//        codeModel.setContents(ss);
//        codeModel.setWidth(400);
//        codeModel.setHeight(400);
//        codeModel.setFontSize(15);
//        codeModel.setLogoFile(new File("G:/ACODE/29936672.jpg"));
//        codeModel.setDesc("国大金象大药房优惠码");

//        code.createCodeImage(ss,"D:/acode/test.jpg");
        FileInputStream fis = new FileInputStream("D:/acode/mobike.png");
        System.out.println(code.decode(fis));
    }
}
