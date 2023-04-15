package com.maciejwalkowiak.assertj.generator;

import java.util.List;

import com.squareup.javapoet.MethodSpec;

public class CommonAssertionMethodGenerator implements AssertionMethodGenerator {
    @Override
    public List<MethodSpec> generate(ClassDescription classDescription, MethodDescription methodDescription) {
        return List.of();
    }
}
