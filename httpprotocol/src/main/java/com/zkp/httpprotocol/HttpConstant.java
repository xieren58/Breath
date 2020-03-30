package com.zkp.httpprotocol;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/9/12
 */
public interface HttpConstant {

    /**
     * 返回数据结构
     */
    interface PROTOCOL {
        String SUCCESS = "success"; // 不需要返回给用户
        String TYPE = "type";       // 不需要返回给用户
        String STATUS = "status";   // 需要
        String CODE = "code";       // 不需要返回给用户
        String TOKEN = "token";     // 需要
        String OBJ = "obj";         // 需要
        String MESSAGE = "message"; // 暂时不需要
        String FILENAME = "fileName";   // 暂时不需要
    }

    /**
     * 通用状态码
     *
     * @see <a href="https://www.showdoc.cc/115831915584390?page_id=705140884713225"/>
     */
    interface CommonCode {
        // 已登录
        String LOGIN = "10000";
        // 未登录
        String NOT_LOGIN = "10001";
        // 请求成功
        String REQ_OK = "10200";
        // 系统异常
        String SYS_EXCEPTION = "10400";
        // 已存在
        String EXIST = "10403";
        // 找不到
        String NOT_FOUND = "10404";
        // 超时
        String TIME_OUT = "10408";
        // 失效
        String INVALID = "10409";
        // 参数错误
        String PARAMS_ERROR = "10500";
        // 请求太频繁
        String WAIT = "10600";
    }

    /**
     * 通用状态码
     *
     * @see <a href="https://www.showdoc.cc/115831915584390?page_id=705143845436534"/>
     */
    interface ConsumerCode {
        // 已锁定
        String LOCKED = "90401";
        // 失效/未启用
        String INVALID = "90402";
    }


    /**
     * 自定义的状态码，用于对接口返回数据的判断过滤
     * 注意和后台接口文档定义的状态码无任何关系
     */
    interface CustomCode {
        // 返回数据为空
        String RESPONSE_EMPTY = "response_empty";
        String NON_SUCCESS = "non_success";
        String NO_OBJ_FIELD = "no_obj_field";
        String OBJ_JSON_EMPTY = "obj_json_empty";

    }

    interface NewApiCode {
        // 成功
        int OK = 200;
        // 未授权
        int UNAUTHORIZED = 401;
        // 禁止
        int FORBIDDEN = 403;
        // 没找到
        int NOT_FOUND = 404;
    }
}
