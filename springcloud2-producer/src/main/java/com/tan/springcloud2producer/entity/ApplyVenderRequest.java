package com.tan.springcloud2producer.entity;

public class ApplyVenderRequest {
    private String venderName;
    private String contact;
    private String category;
    private Integer generalTaxpayer;
    private Integer moreTwoYear;
    private Integer license;
    private Integer vender;
    private Integer advantage;
    private Integer kinsfolk;
    private String pin;
    private String detail;

    public ApplyVenderRequest() {
    }

    public String getVenderName() {
        return this.venderName;
    }

    public String getContact() {
        return this.contact;
    }

    public String getCategory() {
        return this.category;
    }

    public Integer getGeneralTaxpayer() {
        return this.generalTaxpayer;
    }

    public Integer getMoreTwoYear() {
        return this.moreTwoYear;
    }

    public Integer getLicense() {
        return this.license;
    }

    public Integer getVender() {
        return this.vender;
    }

    public Integer getAdvantage() {
        return this.advantage;
    }

    public Integer getKinsfolk() {
        return this.kinsfolk;
    }

    public String getPin() {
        return this.pin;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGeneralTaxpayer(Integer generalTaxpayer) {
        this.generalTaxpayer = generalTaxpayer;
    }

    public void setMoreTwoYear(Integer moreTwoYear) {
        this.moreTwoYear = moreTwoYear;
    }

    public void setLicense(Integer license) {
        this.license = license;
    }

    public void setVender(Integer vender) {
        this.vender = vender;
    }

    public void setAdvantage(Integer advantage) {
        this.advantage = advantage;
    }

    public void setKinsfolk(Integer kinsfolk) {
        this.kinsfolk = kinsfolk;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApplyVenderRequest)) {
            return false;
        } else {
            ApplyVenderRequest other = (ApplyVenderRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label143: {
                    Object this$venderName = this.getVenderName();
                    Object other$venderName = other.getVenderName();
                    if (this$venderName == null) {
                        if (other$venderName == null) {
                            break label143;
                        }
                    } else if (this$venderName.equals(other$venderName)) {
                        break label143;
                    }

                    return false;
                }

                Object this$contact = this.getContact();
                Object other$contact = other.getContact();
                if (this$contact == null) {
                    if (other$contact != null) {
                        return false;
                    }
                } else if (!this$contact.equals(other$contact)) {
                    return false;
                }

                Object this$category = this.getCategory();
                Object other$category = other.getCategory();
                if (this$category == null) {
                    if (other$category != null) {
                        return false;
                    }
                } else if (!this$category.equals(other$category)) {
                    return false;
                }

                label122: {
                    Object this$generalTaxpayer = this.getGeneralTaxpayer();
                    Object other$generalTaxpayer = other.getGeneralTaxpayer();
                    if (this$generalTaxpayer == null) {
                        if (other$generalTaxpayer == null) {
                            break label122;
                        }
                    } else if (this$generalTaxpayer.equals(other$generalTaxpayer)) {
                        break label122;
                    }

                    return false;
                }

                label115: {
                    Object this$moreTwoYear = this.getMoreTwoYear();
                    Object other$moreTwoYear = other.getMoreTwoYear();
                    if (this$moreTwoYear == null) {
                        if (other$moreTwoYear == null) {
                            break label115;
                        }
                    } else if (this$moreTwoYear.equals(other$moreTwoYear)) {
                        break label115;
                    }

                    return false;
                }

                Object this$license = this.getLicense();
                Object other$license = other.getLicense();
                if (this$license == null) {
                    if (other$license != null) {
                        return false;
                    }
                } else if (!this$license.equals(other$license)) {
                    return false;
                }

                Object this$vender = this.getVender();
                Object other$vender = other.getVender();
                if (this$vender == null) {
                    if (other$vender != null) {
                        return false;
                    }
                } else if (!this$vender.equals(other$vender)) {
                    return false;
                }

                label94: {
                    Object this$advantage = this.getAdvantage();
                    Object other$advantage = other.getAdvantage();
                    if (this$advantage == null) {
                        if (other$advantage == null) {
                            break label94;
                        }
                    } else if (this$advantage.equals(other$advantage)) {
                        break label94;
                    }

                    return false;
                }

                label87: {
                    Object this$kinsfolk = this.getKinsfolk();
                    Object other$kinsfolk = other.getKinsfolk();
                    if (this$kinsfolk == null) {
                        if (other$kinsfolk == null) {
                            break label87;
                        }
                    } else if (this$kinsfolk.equals(other$kinsfolk)) {
                        break label87;
                    }

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

                Object this$detail = this.getDetail();
                Object other$detail = other.getDetail();
                if (this$detail == null) {
                    if (other$detail != null) {
                        return false;
                    }
                } else if (!this$detail.equals(other$detail)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApplyVenderRequest;
    }


}
