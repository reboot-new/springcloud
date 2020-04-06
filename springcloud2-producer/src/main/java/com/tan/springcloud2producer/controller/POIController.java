package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.helper.PdfHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/poi")
public class POIController {

    /**
     * 
     * @throws Exception
     */
    @RequestMapping("nssm")
    public void nssmFaild() throws Exception{
        File inputWord = new File("D:\\ExportFile\\1.docx");
        File outputFile = new File("D:\\ExportFile\\1.pdf");
        PdfHelper.docx2pdf(inputWord,outputFile);
    }
}
