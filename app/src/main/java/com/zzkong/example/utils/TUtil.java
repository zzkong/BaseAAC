package com.zzkong.example.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by lico on 2018/5/7
 */
public class TUtil {
    public static <T> Class<T> getT1(Object o, int i) {
        try {
            Type genType = o.getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) params[i];
            return clazz;
        } catch (ClassCastException e) {
        }
        return null;

    }

    public static <T> T getT(Object o, int i) {
        try {
            Type type = o.getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType){
                return ((Class<T>) ((ParameterizedType) (type)).getActualTypeArguments()[i])
                        .newInstance();
            }else if(type instanceof Class){
                ParameterizedType typ = (ParameterizedType) ((Class)type).getGenericSuperclass();
                return ((Class<T>)typ.getActualTypeArguments()[i]).newInstance();
            }
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }
}
