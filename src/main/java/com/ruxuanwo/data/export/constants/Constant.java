package com.ruxuanwo.data.export.constants;

/**
 * 常量类
 *
 * @author xiepuyao
 * @date Created on 2017/11/24
 */
public final class Constant {


    /**
     * 请求头token
     */
    public static final String HTTP_HEADER_ACCESS_TOKEN = "Access-Token";

    /**
     * @Fields RESULT_CODE_SUCCESS : 返回码成功
     */
    public static final int RESULT_CODE_SUCCESS = 1;

    /**
     * @Fields RESULT_CODE_FAILURE : 返回码失败
     */
    public static final int RESULT_CODE_FAILURE = 0;
    /**
     * @Fields RESULT_CODE_EXCEPTION : 请求抛出异常
     */
    public static final int RESULT_CODE_EXCEPTION = 1002;

    /**
     * @Fields RESULT_CODE_NOT_LOGIN : 未登陆状态
     */
    public static final int RESULT_CODE_NOT_LOGIN = 1003;

    /**
     * @Fields RESULT_CODE_NO_EXISTS : 查询结果为空
     */
    public static final int RESULT_CODE_NO_EXISTS = 1004;

    /**
     * @Fields RESULT_CODE_NOT_AUTHORIZED : 无操作权限
     */
    public static final int RESULT_CODE_NOT_AUTHORIZED = 1005;

    /**
     * @Fields RESULT_CODE_USER_OR_PASSWORD_ERROR : 返回码 用户名或密码错误
     */
    public static final int RESULT_CODE_USER_OR_PASSWORD_ERROR = 1006;

    //
    /**
     * @Fields RESULT_CODE_MAX_PASSWORD_ERROR_COUNT : 返回码 密码错误次数达到上限
     */
    public static final int RESULT_CODE_MAX_PASSWORD_ERROR_COUNT = 1007;

    /**
     * @Fields RESULT_CODE_ILLEGAL_REQUEST : 非法请求返回码 未登录 或跳过登陆的请求
     */
    public static final int  RESULT_CODE_ILLEGAL_REQUEST = 1008;

    /**
     * @Fields RESULT_CODE_PARAMS_EMPTY : 返回码 必填参数为空
     */
    public static final int  RESULT_CODE_PARAMS_EMPTY = 1009;


    /**
     * @Fields RESULT_CODE_MODULE_REGISTER_MODULE_ALREADY_EXISTED : 模块注册返回码： 模块已存在
     */
    public static final int RESULT_CODE_MODULE_REGISTER_MODULE_ALREADY_EXISTED = 2001;

    /**
     * @Fields RESULT_CODE_MODULE_REGISTER_ILLEGAL_REQUEST : 模块注册返回码：非法请求
     */
    public static final int RESULT_CODE_MODULE_REGISTER_ILLEGAL_REQUEST = 2002;

    /**
     * @Fields RESULT_CODE_MODULE_REGISTER_GET_MODULE_CONFIG_FAILURE : 模块注册返回码：获取模块配置信息失败
     */
    public static final int RESULT_CODE_MODULE_REGISTER_GET_MODULE_CONFIG_FAILURE = 2003;

    /**
     * @Fields RESULT_CODE_MODULE_REGISTER_GET_OMS_APP_CONFIG_FAILURE : 模块注册返回码：获取OMS App配置信息失败
     */
    public static final int RESULT_CODE_MODULE_REGISTER_GET_OMS_APP_CONFIG_FAILURE = 2004;

    /**
     * @Fields RESULT_MSG_SUCCESS : 返回消息 成功
     */
    public static final String RESULT_MSG_SUCCESS = "Success";

    /**
     * @Fields RESULT_MSG_FAILURE : 返回消息 失败
     */
    public static final String RESULT_MSG_FAILURE = "Failure";

    /**
     * @Fields RESULT_MSG_USER_OR_PASSWORD_ERROR : 返回消息  用户名或密码错误
     */
    public static final String RESULT_MSG_USER_OR_PASSWORD_ERROR = "username or password Invalid";

    /**
     * @Fields RESULT_MSG_MAX_PASSWORD_ERROR_COUNT : 返回消息 密码错误次数达到上限
     */
    public static final String RESULT_MSG_MAX_PASSWORD_ERROR_COUNT = "upper limit for password error";

    /**
     * @Fields RESULT_MSG_ILLEGAL_REQUEST : 非法请求返回码 未登录 或跳过登陆的请求
     */
    public static final String  RESULT_MSG_ILLEGAL_REQUEST = "Illegal request";

    /**
     * @Fields RESULT_MSG_EXCEPTION : 返回消息 请求抛出异常
     */
    public static final String RESULT_MSG_EXCEPTION = "request exception";

    /**
     * @Fields RESULT_MSG_PARAMS_EMPTY : 返回消息 必填参数为空
     */
    public static final String  RESULT_MSG_PARAMS_EMPTY = "the input parameter is null";

    /**
     * 启用
     */
    public static final int STATE_START = 1;
    /**
     * 禁用
     */
    public static final int STATE_DISABLED = -1;
    /**
     * 停用
     */
    public static final int STATE_STOP = 0;
    /**
     * 解密解密的KEY值
     */
    public static final String KEY = "uhope";
    /**
     * 主键生成方式：1 自增
     */
    public static final Integer AUTOINCREMENT = 1;
    /**
     * 主键生成方式：2 UUID
     */
    public static final Integer UUID = 2;
    /**
     * 主键生成方式：3 自定义
     */
    public static final Integer CUSTOMIZE = 3;
    /**
     * 转换失败
     */
    public static final String REGION_ERROR = "行政区域转换失败";
    public static final String REACH_ERROR = "河段转换失败";
    public static final String RIVER_ERROR = "河流转换失败";
    public static final String SHORE_ERROR = "岸别转换失败";
    public static final String ISNOTNULL = "转换值不能为空";
    public static final String SECTION_ERROR = "断面转换失败";
    /**
     * 转换为空
     */
    public static final String REGION_NULL = "数据库转换值为空";
    /**
     * 临时表字段前缀
     */
    public static final String TABEL_FIELD = "field_";
    /**
     * 临时表前缀
     */
    public static final String TABLE_PREFIX = "ed_temp_";
    /**
     * 新数据临时表后缀
     */
    public static final String TABLE_SUFFIX = "_new";

    /**
     * 长度校验器名称
     */
    public static final String LENGCHECK_NAME = "长度校验";
    /**
     * 整行数据正确
     */
    public static final String DATA_RIGHT = "无";

    /**
     * 缺省值
     */
    public static final String DEFAULTVALUE = "缺省值";
    /**
     * 行政区域redis的key
     */
    public static final String REGIONREDIS = "regionDTO";

    public static final String RIVER_SHORE = "河段岸别转换器";
    public static final String LAKES_SHORE = "湖段岸别转换器";
    public static final String RESERVOIR_SHORE = "库段岸别转换器";

    public static final String RIVER_REACH_PID = "110";
    public static final String LAKES_REACH_PID = "112";
    public static final String RESERVOIR_REACH_PID = "114";
}
