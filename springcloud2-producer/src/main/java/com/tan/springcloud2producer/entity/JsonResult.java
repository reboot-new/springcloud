package com.tan.springcloud2producer.entity;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1550436928385370645L;

    private boolean success = false;

    private String msg = "";

    private Object data = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
