package com.ruxuanwo.data.export.exception;

/**
 * @Author: ChenBin
 * @Date: 2018/8/28/0028 9:09
 */
public class ConversionException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "转换器过程出错！";
    private String message;


    public ConversionException(){
        super();
    }

    public ConversionException(String message) {
        super();
        this.message = message;
    }

    public ConversionException(Throwable throwable){
        super(throwable);
    }

    @Override
    public String getMessage() {
        return message == null ? DEFAULT_MESSAGE : message;
    }

}
