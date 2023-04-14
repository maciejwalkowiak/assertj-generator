package com.maciejwalkowiak.assertj.generator;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

public class DefaultEntryPointMethodGenerator implements EntryPointMethodGenerator {
    @Override
    public MethodSpec generate(ClassDescription classDescription) {
        return MethodSpec.methodBuilder("assertThat")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(classDescription.getAssertionClassName()))
                .addParameter(classDescription.getClazz(), "it")
                .addStatement("return new $T(it)", ClassName.bestGuess(classDescription.getAssertionClassName()))
                .build();
    }
}
