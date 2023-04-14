package com.maciejwalkowiak.assertj.generator;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

public class DefaultConstructorGenerator implements ConstructorGenerator {
    @Override
    public MethodSpec generate(ClassDescription classDescription) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(classDescription.getClazz(), "it")
                .addStatement("super(it, $T.class)", ClassName.bestGuess(classDescription.getAssertionClassName())).build();
    }
}
