package com.maciejwalkowiak.assertj.generator;

import java.util.List;
import java.util.function.Consumer;

import javax.lang.model.element.Modifier;

import com.maciejwalkowiak.assertj.generator.util.StringUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.assertj.core.api.Assertions;

public class DefaultAssertionMethodGenerator implements AssertionMethodGenerator {
    @Override
    public List<MethodSpec> generate(ClassDescription classDescription, MethodDescription methodDescription) {
        MethodSpec main = MethodSpec.methodBuilder(MethodNames.has(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.getAssertionClassName()))
                .addParameter(methodDescription.returnType(), "arg")
                .addStatement("$T.assertThat(actual.get$L()).isEqualTo($L)", Assertions.class,
                        StringUtils.capitalize(methodDescription.fieldName()), "arg")
                .addStatement("return this")
                .build();

        MethodSpec satisfying = MethodSpec.methodBuilder(MethodNames.hasSatisfying(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.getAssertionClassName()))
                .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(methodDescription.returnType())), "arg")
                .addStatement("$T.assertThat(actual.$L()).satisfies($L)", Assertions.class,
                        methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();
        return List.of(main, satisfying);
    }
}
