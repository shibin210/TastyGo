package com.itheima.reggie.common;

/**
 * 基于 ThreadLocal 封装工具类，用户保存和获取当前登录用户id。
 */
public class _BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * set value
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * get value
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
