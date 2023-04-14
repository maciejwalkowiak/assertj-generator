package com.maciejwalkowiak.assertj.generator;

import java.util.List;

import org.reflections.Reflections;

import static org.reflections.ReflectionUtils.Methods;

class ClassDescription {
    private final List<MethodDescription> methods;
    private final Class<?> clazz;

    static ClassDescription of(Class<?> clazz) {
        List<MethodDescription> methods = new Reflections(clazz.getPackageName()).get(
                        Methods.of(clazz)
                                .filter(it -> it.getName().startsWith("get")))
                .stream()
                .map(MethodDescription::of)
                .toList();
        return new ClassDescription(clazz, methods);
    }

    ClassDescription(Class<?> clazz, List<MethodDescription> methods) {
        this.clazz = clazz;
        this.methods = methods;
    }

    List<MethodDescription> getMethods() {
        return methods;
    }

    Class<?> getClazz() {
        return clazz;
    }

    String getAssertionClassName() {
        return clazz.getSimpleName() + "Assert";
    }

    Generator generator() {
        return new Generator(new GeneratorOptions())
                .addClass(clazz);
    }

    String packageName() {
        return clazz.getPackageName();
    }
}
