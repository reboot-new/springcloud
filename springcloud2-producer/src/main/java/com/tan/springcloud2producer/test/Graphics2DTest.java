package com.tan.springcloud2producer.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Graphics2DTest {

    // 色差范围0~255
    public static int color_range = 210;

    public static void main(String[] args) throws IOException  {

        fillP();
        BufferedImage d = loadImageLocal("D:\\12.png");
        BufferedImage b = loadImageLocal("E:\\Elitel\\03_公司项目\\01_贵州水文项目\\01_文档资料\\等值面图片底图\\A683283F161524753A8FB6D4A004A17E.png");
        writeImageLocal("D:/new10.jpg", modifyImagetogeter(d, b));
        //将多张图片合在一起
        System.out.println("success");
//      hyalinize();
    }

    /**
     * 导入本地图片到缓冲区
     */
    public static BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "png", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {
        try {
            int w = b.getWidth();
            int h = b.getHeight();
            Graphics2D  g = d.createGraphics();
            g.drawImage(b, 0, 0, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return d;
    }


    public static void fillP() throws IOException {
        File f = new File("D:\\2.png");
        BufferedImage image = ImageIO.read(f);
//        BufferedImage image = new BufferedImage(400, 500, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = image.createGraphics();

        // 设置颜色--绿色
        graphics.setColor(new Color(255,0,45,125));

        if (1 == 0) {
            //画树
            Polygon polygon = new Polygon();
            polygon.addPoint(200, 100);
            polygon.addPoint(180, 150);
            polygon.addPoint(220, 150);
            graphics.draw(polygon);
            polygon.reset();
            polygon.addPoint(200, 150);
            polygon.addPoint(160, 200);
            polygon.addPoint(240, 200);
            graphics.draw(polygon);
            polygon.reset();
            polygon.addPoint(200, 200);
            polygon.addPoint(140, 250);
            polygon.addPoint(260, 250);
            graphics.draw(polygon);
            polygon.reset();
            polygon.addPoint(180, 250);
            polygon.addPoint(180, 300);
            polygon.addPoint(220, 300);
            polygon.addPoint(220, 250);
            graphics.draw(polygon);
        }

        Polygon polygon = new Polygon();
        polygon.addPoint(200, 100);
        polygon.addPoint(440, 100);
        polygon.addPoint(420, 350);
        polygon.addPoint(180, 350);
        polygon.addPoint(200, 100);
        polygon.contains(200,100);
        graphics.fillPolygon(polygon);

        int col = image.getRGB(325,226);

//        polygon.reset();
//        polygon.addPoint(215,120);
//        polygon.addPoint(220,140);
//        polygon.addPoint(200,140);
//        graphics.setColor(new Color(255,255,255,125));
//        graphics.fillPolygon(polygon);
        graphics.dispose();
        File file = new File("D:\\11.png");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(image, "png", fos);
        fos.close();
    }

    public static void hyalinize() throws IOException{
        BufferedImage image = ImageIO.read(new File("D:\\99.png"));
        // 高度和宽度
        int height = image.getHeight();
        int width = image.getWidth();

        // 生产背景透明和内容透明的图片
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics(); // 获取画笔
        g2D.drawImage(imageIcon.getImage(), 0, 0, null); // 绘制Image的图片


        int alpha = 0; // 图片透明度
        // 外层遍历是Y轴的像素
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            // 内层遍历是X轴的像素
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // 对当前颜色判断是否在指定区间内
                if (colorInRange(rgb)) {
                    alpha = 0;
                } else {
                    // 设置为不透明
                    alpha = 255;
                }
                // #AARRGGBB 最前两位为透明度
                rgb = (alpha << 24) | (rgb & 0x00ffffff);
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        // 绘制设置了RGB的新图片
        g2D.drawImage(bufferedImage, 0, 0, null);

        // 生成图片为PNG
        ImageIO.write(bufferedImage, "png", new File("D:\\2.png"));
        System.out.println("完成画图");
    }

    public static void hyalinize(BufferedImage bufferedImage,Graphics2D g2D){
        int alpha = 0; // 图片透明度
        // 外层遍历是Y轴的像素
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            // 内层遍历是X轴的像素
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // 对当前颜色判断是否在指定区间内
                if (colorInRange(rgb)) {
                    alpha = 0;
                } else {
                    // 设置为不透明
                    alpha = (rgb>>24) & 0x000000FF;
                }
                // #AARRGGBB 最前两位为透明度
                rgb = (alpha << 24) | (rgb & 0x00ffffff);
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        // 绘制设置了RGB的新图片
        g2D.drawImage(bufferedImage, 0, 0, null);
    }

    // 判断是背景还是内容
    public static boolean colorInRange(int color) {
        int alpha = (color >> 24 ) & 0x000000ff; //获取透明度
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        // 通过RGB三分量来判断当前颜色是否在指定的颜色区间内
        if ((red >= color_range && green >= color_range && blue >= color_range) || alpha == 0) {
            return true;
        }
        return false;
    }
}
