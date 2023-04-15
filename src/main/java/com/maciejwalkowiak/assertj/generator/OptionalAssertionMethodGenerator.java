package com.maciejwalkowiak.assertj.generator;

import java.util.List;
import java.util.function.Consumer;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.assertj.core.api.Assertions;

public class OptionalAssertionMethodGenerator implements AssertionMethodGenerator {

    @Override
    public List<MethodSpec> generate(ClassDescription classDescription, MethodDescription methodDescription) {
        MethodSpec main = MethodSpec.methodBuilder(MethodNames.has(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addParameter(methodDescription.genericReturnType(0), "arg")
                .addStatement("$T.assertThat(actual.$L()).hasValue($L)", Assertions.class,
                        methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();

        MethodSpec satisfying = MethodSpec.methodBuilder(MethodNames.hasSatisfying(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addParameter(ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(methodDescription.genericReturnType(0))), "arg")
                .addStatement("$T.assertThat(actual.$L()).hasValueSatisfying($L)", Assertions.class,
                        methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();
        return List.of(main, satisfying);
    }
}
