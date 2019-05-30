package com.ruxuanwo.data.export.exception;

/**
 * @Author: ChenBin
 * @Date: 2018/7/13/0013 10:16
 */
public class CheckErrorException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "校验结果错误！";
    private String message;

    public CheckErrorException(String message) {
        super();
        this.message = message;
    }

    public CheckErrorException() {
        super();
    }

    public CheckErrorException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message == null ? DEFAULT_MESSAGE : message;
    }
}
