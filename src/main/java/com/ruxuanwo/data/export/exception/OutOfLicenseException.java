package com.ruxuanwo.data.export.exception;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/25/0025 14:22
 */
public class OutOfLicenseException extends RuntimeException {

    /**
     * 所有许可证
     */
    private Integer allLicense;
    /**
     * 已使用许可证
     */
    private Integer useLicense;
    /**
     * 剩余许可证
     */
    private Integer remainLicense;

    private String message;

    public OutOfLicenseException() {
        super();
    }

    public OutOfLicenseException(Integer allLicense, Integer useLicense, String message) {
        super();
        this.allLicense = allLicense;
        this.useLicense = useLicense;
        this.remainLicense = allLicense - useLicense;
        this.message = message;
    }

    public OutOfLicenseException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "抱歉,当前导入的河湖长数量已经超出套餐购买限制. 套餐购买license总数" + allLicense +", 已使用数量为" + useLicense + ", 剩余可用数量为" + remainLicense + "";
    }
}
