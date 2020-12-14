package com.tan.springcloud2producer.entity;

public class ApplyServicerRequest {
    private String name;
    private String phone;
    private Integer province;
    private Integer city;
    private Integer county;
    private Integer town;
    private String detail;
    private String pin;

    public ApplyServicerRequest() {
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public Integer getProvince() {
        return this.province;
    }

    public Integer getCity() {
        return this.city;
    }

    public Integer getCounty() {
        return this.county;
    }

    public Integer getTown() {
        return this.town;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getPin() {
        return this.pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public void setTown(Integer town) {
        this.town = town;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApplyServicerRequest)) {
            return false;
        } else {
            ApplyServicerRequest other = (ApplyServicerRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label107;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label107;
                    }

                    return false;
                }

                Object this$phone = this.getPhone();
                Object other$phone = other.getPhone();
                if (this$phone == null) {
                    if (other$phone != null) {
                        return false;
                    }
                } else if (!this$phone.equals(other$phone)) {
                    return false;
                }

                Object this$province = this.getProvince();
                Object other$province = other.getProvince();
                if (this$province == null) {
                    if (other$province != null) {
                        return false;
                    }
                } else if (!this$province.equals(other$province)) {
                    return false;
                }

                label86: {
                    Object this$city = this.getCity();
                    Object other$city = other.getCity();
                    if (this$city == null) {
                        if (other$city == null) {
                            break label86;
                        }
                    } else if (this$city.equals(other$city)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$county = this.getCounty();
                    Object other$county = other.getCounty();
                    if (this$county == null) {
                        if (other$county == null) {
                            break label79;
                        }
                    } else if (this$county.equals(other$county)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$town = this.getTown();
                    Object other$town = other.getTown();
                    if (this$town == null) {
                        if (other$town == null) {
                            break label72;
                        }
                    } else if (this$town.equals(other$town)) {
                        break label72;
                    }

                    return false;
                }

                Object this$detail = this.getDetail();
                Object other$detail = other.getDetail();
                if (this$detail == null) {
                    if (other$detail != null) {
                        return false;
                    }
                } else if (!this$detail.equals(other$detail)) {
                    return false;
                }

                Object this$pin = this.getPin();
                Object other$pin = other.getPin();
                if (this$pin == null) {
                    if (other$pin != null) {
                        return false;
                    }
                } else if (!this$pin.equals(other$pin)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApplyServicerRequest;
    }

}
