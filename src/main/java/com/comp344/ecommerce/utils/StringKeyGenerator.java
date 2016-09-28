package com.comp344.ecommerce.utils;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Created by Byambatsog on 9/27/16.
 */
public class StringKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Class<?>[] types = method.getParameterTypes();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<types.length; i++){
            Class type = types[i];
            Object param = params[i];
            sb.append(type.getSimpleName()).append(":").append(param).append(",");
        }
        return sb.toString();
    }
}
