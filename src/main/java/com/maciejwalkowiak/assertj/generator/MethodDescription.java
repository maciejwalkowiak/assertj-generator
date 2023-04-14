package com.maciejwalkowiak.assertj.generator;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.maciejwalkowiak.assertj.generator.util.StringUtils;

final class MethodDescription {
    private final Method method;

    static MethodDescription of(Method method) {
        return new MethodDescription(method);
    }

    MethodDescription(Method method) {
        this.method = method;
    }

    String fieldName() {
        return StringUtils.uncapitalize(method.getName().replace("get", ""));
    }

    public String methodName() {
        return method.getName();
    }

    Class<?> returnType() {
        return method.getReturnType();
    }

    Type genericReturnType(int index) {
        return ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[index];
    }
}
