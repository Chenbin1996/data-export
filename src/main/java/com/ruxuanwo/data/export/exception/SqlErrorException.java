package com.ruxuanwo.data.export.exception;


import com.ruxuanwo.data.export.core.ServiceException;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/25/0025 14:22
 */
public class SqlErrorException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "数据库用户名或密码错误！";
    private String message;

    public SqlErrorException() {
        super();
    }

    public SqlErrorException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message == null ? DEFAULT_MESSAGE : message;
    }
}
