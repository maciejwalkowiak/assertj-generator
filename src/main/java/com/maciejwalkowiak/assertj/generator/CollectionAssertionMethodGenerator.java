package com.maciejwalkowiak.assertj.generator;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.assertj.core.api.Assertions;

public class CollectionAssertionMethodGenerator implements AssertionMethodGenerator {
    @Override
    public List<MethodSpec> generate(ClassDescription classDescription, MethodDescription methodDescription) {
        return List.of(
                hasNo(classDescription, methodDescription),
                hasVarArgArray(classDescription, methodDescription),
                hasCollection(classDescription, methodDescription)
//                hasSatisfying(classDescription, methodDescription)
        );
    }

    private static MethodSpec hasSatisfying(ClassDescription classDescription, MethodDescription methodDescription) {
        TypeVariableName wildcardType = TypeVariableName.get("?", ClassName.get(methodDescription.genericReturnType(0)));

        return MethodSpec.methodBuilder(MethodNames.hasSatisfying(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addParameter(
                        ParameterizedTypeName.get(ClassName.get(Consumer.class), ParameterizedTypeName.get(ClassName.get(methodDescription.returnType()), wildcardType.withBounds(methodDescription.genericReturnType(0)))),
                        "arg")
                .addStatement("$T.assertThat(actual.$L()).satisfies($L)", Assertions.class,
                        methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();
    }

    private static MethodSpec hasNo(ClassDescription classDescription, MethodDescription methodDescription) {
        return MethodSpec.methodBuilder(MethodNames.hasNo(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addStatement("$T.assertThat(actual.$L()).isEmpty()", Assertions.class, methodDescription.methodName())
                .addStatement("return this")
                .build();
    }

    private static MethodSpec hasVarArgArray(ClassDescription classDescription, MethodDescription methodDescription) {
        TypeName parameterType = ArrayTypeName.of(methodDescription.genericReturnType(0));

        return MethodSpec.methodBuilder(MethodNames.has(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addParameter(parameterType, "arg")
                .varargs(true)
                .addStatement("$T.assertThat(actual.$L()).contains($L)", Assertions.class, methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();
    }

    private static MethodSpec hasCollection(ClassDescription classDescription, MethodDescription methodDescription) {
        TypeName collectionParameterType = ParameterizedTypeName.get(Collection.class, methodDescription.genericReturnType(0));

        return MethodSpec.methodBuilder(MethodNames.has(methodDescription))
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.bestGuess(classDescription.assertionClassName()))
                .addParameter(collectionParameterType, "arg")
                .addStatement("$T.assertThat(actual.$L()).containsAll($L)", Assertions.class, methodDescription.methodName(), "arg")
                .addStatement("return this")
                .build();
    }
}
