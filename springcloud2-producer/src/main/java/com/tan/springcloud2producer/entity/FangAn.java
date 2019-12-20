package com.tan.springcloud2producer.entity;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.PictureRenderData;

import java.util.List;
import java.util.Map;

public class FangAn {
    private String home_title;

    public String getHome_title() {
        return home_title;
    }

    public void setHome_title(String home_title) {
        this.home_title = home_title;
    }

    public String getHome_date() {
        return home_date;
    }

    public void setHome_date(String home_date) {
        this.home_date = home_date;
    }


    private String home_date;

    public List<Map<String, String>> getDetailTable() {
        return detailTable;
    }

    public void setDetailTable(List<Map<String, String>> detailTable) {
        this.detailTable = detailTable;
    }

    @Name("stWeightTable")
    private List<Map<String,String>> detailTable;

    public String[][] getYubaoTable() {
        return yubaoTable;
    }

    public void setYubaoTable(String[][] yubaoTable) {
        this.yubaoTable = yubaoTable;
    }

    @Name("yubaoTable")
    private String[][] yubaoTable;

    public PictureRenderData getPic() {
        return pic;
    }

    public void setPic(PictureRenderData pic) {
        this.pic = pic;
    }

    private PictureRenderData pic;
}
