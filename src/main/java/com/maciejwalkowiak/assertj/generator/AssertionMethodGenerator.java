package com.maciejwalkowiak.assertj.generator;

import java.util.List;

import com.squareup.javapoet.MethodSpec;

public interface AssertionMethodGenerator {

    List<MethodSpec> generate(ClassDescription classDescription, MethodDescription methodDescription);
}
