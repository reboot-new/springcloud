package com.tan.springcloud2producer.helper;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TikaHelper {

    public static void main(String[] args) {

//        TikaHelper.getContextParser("D:\\Nginx\\nginx-1.14.0\\conf\\nginx.conf1");

        System.out.println(getPlaintext(new File("C:\\Users\\Administrator\\Desktop\\temp\\博客笔记.doc")));

//        FileHelper fh  = new FileHelper();
//        List<File> fs  = fh.getAllFile(new File("F:\\Tencent Files\\1928212168\\FileRecv\\�½��ļ���\\�½��ļ���"));
//        for (File f: fs) {
//            System.out.println(f.getName());
//            TikaHelper.getContextParser(f.getAbsolutePath());
//        }
        System.out.println("hello world ...你好");
    }

    /**
     * 获取指定文件里面的内容
     * created by tan_alpha on 2019-11-8
     * @param file 需要解析的文件
     * @return 文件解析的内容
     */
    public static String getContextParser(File file){
        if (!file.isFile()){return null;}
        String context = parse(file);
        return context;
    }

    /**
     * 获取文本里面的内容
     * created by tan_alpha on 2019-11-8
     * @param file 需要解析的文件
     * @return 文件解析的内容
     */
    public static String getPlaintext(File file){
        if (!file.isFile()){return null;}
        String context = "";
        try {
            //detecting the file type
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            FileInputStream inputstream = new FileInputStream(file);
            ParseContext pcontext=new ParseContext();

            //Text document parser
            TXTParser TexTParser = new TXTParser();
            TexTParser.parse(inputstream, handler, metadata,pcontext);
            context = handler.toString();

        }catch (IOException e){
            e.printStackTrace();
        }catch (TikaException te){
            te.printStackTrace();
        }catch (SAXException saex){
            saex.printStackTrace();
        }
        return context;
    }


    /**
     * 解析文件
     * @param file
     * @return
     */
    private static String parse(File file){

        InputStream fileInputStream = null;
        String context = "";
        try{
            fileInputStream = new FileInputStream(file);
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            ParseContext parseContext=new ParseContext();
            parser.parse(fileInputStream,handler,metadata,parseContext);
            context = handler.toString();
        }catch (TikaException tke){
            //根据扩展名解析文件报错时，尝试使用文本文件解析
            context = getPlaintext(file);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (SAXException saxe){
            saxe.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return context;
    }
}
