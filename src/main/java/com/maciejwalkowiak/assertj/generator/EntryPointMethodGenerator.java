package com.maciejwalkowiak.assertj.generator;

import com.squareup.javapoet.MethodSpec;

@FunctionalInterface
public interface EntryPointMethodGenerator {

    MethodSpec generate(ClassDescription classDescription);
}
