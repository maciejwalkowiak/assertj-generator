package com.maciejwalkowiak.assertj.generator;

import com.squareup.javapoet.MethodSpec;

@FunctionalInterface
public interface ConstructorGenerator {
    MethodSpec generate(ClassDescription classDescription);
}
