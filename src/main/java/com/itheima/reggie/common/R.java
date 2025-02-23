package com.itheima.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * */
@Data
public class R<T> {
    private Integer code; // 编码：1 成功， 0 和其他数字为失败。
    private String msg; // 错误信息
    private T data; // 数据

    private Map map = new HashMap(); // 动态数据

    /**
     *
     * @param data
     * @return r
     * @param <T>
     */
    public static <T> R<T> success(T data){
        R<T> r = new R<T>();
        r.data = data;
        r.code = 1;
        return r;
    }

    /**
     *
     * @param msg
     * @return r
     * @param <T>
     */
    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value){
        this.map.put(key, value);
        return this;
    }
}
