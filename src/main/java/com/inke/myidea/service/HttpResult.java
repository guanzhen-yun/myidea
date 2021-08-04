package com.inke.myidea.service;

/**
 * 响应公共封装类
 */
public class HttpResult<Body> {
    public static final int SUCCESS_CODE = 200;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Body getData() {
        return data;
    }

    public void setData(Body data) {
        this.data = data;
    }

    private Body data;

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
