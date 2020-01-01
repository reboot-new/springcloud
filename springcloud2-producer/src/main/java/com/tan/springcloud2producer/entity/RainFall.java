package com.tan.springcloud2producer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rainfall")
public class RainFall extends BaseEntity{
    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getRainfall() {
        return rainfall;
    }

    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Column(name = "stcd")
    private String stcd;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "rainfall")
    private float rainfall;
    @Column(name = "test")
    private String test;
}
