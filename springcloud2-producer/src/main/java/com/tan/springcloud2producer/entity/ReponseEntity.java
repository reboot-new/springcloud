package com.tan.springcloud2producer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReponseEntity implements Serializable {

    public ReponseEntity(){
        waresCategory = new ArrayList<>();
    }
    public List<WaresCategory> getWaresCategory() {
        return waresCategory;
    }

    public void setWaresCategory(List<WaresCategory> waresCategory) {
        this.waresCategory = waresCategory;
    }

    private List<WaresCategory> waresCategory;
}
