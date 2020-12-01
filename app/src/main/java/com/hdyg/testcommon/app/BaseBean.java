package com.hdyg.testcommon.app;

/**
 * @author CZB
 * @describe 基类实体类
 * @time 2020/12/1 19:14
 * @param <T> 泛型实体
 */
public class BaseBean<T> {
    private String status;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
