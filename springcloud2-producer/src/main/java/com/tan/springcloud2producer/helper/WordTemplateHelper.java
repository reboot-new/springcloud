package com.tan.springcloud2producer.helper;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.tan.springcloud2producer.entity.FangAn;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import java.io.*;
import java.util.*;

public class WordTemplateHelper {

    public static void main(String[] args) throws IOException {

        String[][] rows = new String[2][4];
        rows[0][0] = "aaa";
        rows[0][1] = "aaa";
        rows[0][2] = "aaa";
        rows[0][3] = "aaa";

        rows[1][0] = "bb";
        rows[1][1] = "cc";
        rows[1][2] = "dd";
        rows[1][3] = "asaa";

        List<Map<String,String>> table1 = new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        row.put("NO","1");
        row.put("STCD","123213");
        row.put("STNM","张三");
        row.put("WEIGHT","34");
        table1.add(row);
        row = new HashMap<>();
        row.put("NO","2");
        row.put("STCD","2222");
        row.put("STNM","张22三");
        row.put("WEIGHT","3224");
        table1.add(row);

        FangAn f = new FangAn();
        f.setHome_date("2018年22月23日");
        f.setHome_title("测试方案");
        f.setDetailTable(table1);
        f.setYubaoTable(rows);

        f.setPic(new PictureRenderData(120, 120, "E:\\Elitel\\04_部门合作\\04_水利\\水库\\综合调度\\图1.png"));
        Configure config = Configure.newBuilder().customPolicy("stWeightTable", new NormalTablePolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("E:\\Elitel\\04_部门合作\\04_水利\\水库\\综合调度\\新模板.docx",config).render(f);
        FileOutputStream out = new FileOutputStream("E:\\Elitel\\04_部门合作\\04_水利\\水库\\综合调度\\out_template.docx");

        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}

class NormalTablePolicy extends DynamicTableRenderPolicy {

    // 填充数据所在行数
    int goodsStartRow = 1;

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == data) {return;}

        try{
            List<Map<String,String>> rowsData = (List<Map<String,String>>)data;
            RowRenderData labor;
            XWPFTableRow row = table.getRow(1);
            List<XWPFTableCell> cells = row.getTableCells();

            List<TemplateTableCell> keys = new ArrayList<>();

            TemplateTableCell templateCell;
            String templateName = "";
            for(int i = 0;i < cells.size(); ++i){
                templateName = "@elitel";
                templateCell = new TemplateTableCell(templateName,null);
                try {
                    String cellText = cells.get(i).getText();
                    templateName = cellText.substring(1,cellText.length()-1);
                    templateCell = new TemplateTableCell(templateName,cells.get(i));
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    keys.add(i,templateCell);
                }
            }

            TableStyle rowStyle = new TableStyle();
            rowStyle.setAlign(STJc.CENTER);
            table.removeRow(goodsStartRow);
            for (int i = 0; i < rowsData.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(goodsStartRow + i);
                //创建单元格
                for (int j = 0; j < keys.size(); j++) {
                    insertNewTableRow.createCell();
                }
                String[] rowData = new String[keys.size()];
                for (int j = 0;j < keys.size();++j){
                     rowData[j] = rowsData.get(i).containsKey(keys.get(j).getTemplateName())?rowsData.get(i).get(keys.get(j).getTemplateName()):"";
                }
                labor = RowRenderData.build(rowData);

                labor.setRowStyle(rowStyle);
                MiniTableRenderPolicy.Helper.renderRow(table, goodsStartRow+i, labor);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setRowStyle(XWPFTableRow targetRow,List<TemplateTableCell> styles){
        List<XWPFTableCell> cells = targetRow.getTableCells();
        if(cells.size() != styles.size()){
            return;
        }

        TemplateTableCell eStyel = null;
        XWPFTableCell cell = null;
        for(int i =0,n = cells.size();i < n;++i){
            if ((eStyel=styles.get(i)) == null){
                continue;
            }
            cell = cells.get(i);
            cell.setColor(eStyel.getXwpfTableCell().getColor());
            cell.setVerticalAlignment(eStyel.getXwpfTableCell().getVerticalAlignment());
        }
    }

    class TemplateTableCell {
        private XWPFTableCell xwpfTableCell;

        public XWPFTableCell getXwpfTableCell() {
            return xwpfTableCell;
        }

        public void setXwpfTableCell(XWPFTableCell xwpfTableCell) {
            this.xwpfTableCell = xwpfTableCell;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        private String templateName;

        private String color;

        public TemplateTableCell(){}
        public TemplateTableCell(String templateName,XWPFTableCell xwpfTableCell){
            this.templateName = templateName;
            this.xwpfTableCell = xwpfTableCell;
        }
    }

}
