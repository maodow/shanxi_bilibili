package tv.huan.bilibili.bean;

import lib.kalu.frame.mvp.bean.RequestBean;

public class BaseBean<T> extends RequestBean {

    private String status;
    private int code;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public boolean isNext() {
        return true;
    }
}
