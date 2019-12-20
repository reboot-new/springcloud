package com.tan.springcloud2producer.helper;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import java.io.*;

public class PdfHelper {
    public static void main(String[] args) throws Exception {
        File inputWord = new File("E:\\Elitel\\04_部门合作\\04_水利\\水库\\综合调度\\out_picture.docx");
        File outputFile = new File("E:\\Elitel\\04_部门合作\\04_水利\\水库\\综合调度\\out_picture.pdf");
//        docx2pdf(inputWord,outputFile);
//        docx2pdfByPOI(inputWord,outputFile);
        word2pdf(inputWord,outputFile);
    }

    public static void docx2pdf(File docxFile,File pdfFile) throws Exception{

        try(InputStream docxInputStream = new FileInputStream(docxFile))  {
            try( OutputStream outputStream = new FileOutputStream(pdfFile)){
                IConverter converter = LocalConverter.builder().build();
                converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void docx2pdfByPOI(File inFile,File outFile){
        ActiveXComponent app = null;
        String wordFile = inFile.getAbsolutePath();
        String pdfFile = outFile.getAbsolutePath();
        System.out.println("开始转换...");
        // 开始时间
        long start = System.currentTimeMillis();
        try {
            // 打开word
            app = new ActiveXComponent("Word.Application");
            // 设置word不可见,很多博客下面这里都写了这一句话，其实是没有必要的，因为默认就是不可见的，如果设置可见就是会打开一个word文档，对于转化为pdf明显是没有必要的
            //app.setProperty("Visible", false);
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            System.out.println("打开文件: " + wordFile);
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            System.out.println("另存为: " + pdfFile);
            // 另存为，将文档报错为pdf，其中word保存为pdf的格式宏的值是17
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);
            // 结束时间
            long end = System.currentTimeMillis();
            System.out.println("转换成功，用时：" + (end - start) + "ms");
        }catch(Exception e) {
            System.out.println("转换失败"+e.getMessage());
        }finally {
            // 关闭office
            app.invoke("Quit", 0);
        }
    }


    public static void word2pdf(File inFile,File outFile){

        //将目标路径赋值给最终路径
        InputStream inputStream = PdfHelper.class.getClassLoader().getResourceAsStream("aspose-license.xml");
        try {

            //加载license信息
            License license = new License() ;
            license.setLicense(inputStream) ;

            Document document = new Document(inFile.getAbsolutePath());

            //如果没有制定目标文件路径，则自动在当前路径生成pdf文件
//            for(; StringUtils.isEmpty(finalPath);){
//                String fileName  = filePath.substring(filePath.lastIndexOf("/"),filePath.lastIndexOf(".")) ;
//                String filePathWithOutFineName = filePath.substring(0,filePath.lastIndexOf("/")) ;
//
//                finalPath = filePathWithOutFineName + fileName + ".pdf" ;
//                break;
//            }

            //输出流写入pdf文件；
            FileOutputStream outStream = new FileOutputStream(outFile) ;
            document.save(outStream, SaveFormat.PDF) ;
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return finalPath ;
    }
}
