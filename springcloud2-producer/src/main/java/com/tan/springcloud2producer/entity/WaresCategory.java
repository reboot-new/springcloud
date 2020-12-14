package com.tan.springcloud2producer.entity;

public class WaresCategory {
    public WaresCategory(Integer type,String name){
        this.type = type;
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer type;
    private String name;
}
