package com.maciejwalkowiak.assertj.generator;

import java.util.List;

import com.squareup.javapoet.TypeSpec;

public class GenerateResult {
    private final List<GeneratedClass> generatedClasses;
    private final TypeSpec entryPoint;

    GenerateResult(List<GeneratedClass> generatedClasses, TypeSpec entryPoint) {
        this.generatedClasses = generatedClasses;
        this.entryPoint = entryPoint;
    }

    List<GeneratedClass> generatedClasses() {
        return generatedClasses;
    }

    TypeSpec entryPoint() {
        return entryPoint;
    }
}
