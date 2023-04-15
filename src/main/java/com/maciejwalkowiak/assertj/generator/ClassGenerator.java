package com.maciejwalkowiak.assertj.generator;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import org.assertj.core.api.AbstractObjectAssert;

class ClassGenerator {

    TypeSpec generate(ClassDescription classDescription, GeneratorOptions options) {
        String packageName = classDescription.packageName();
        ClassName assertClassName = ClassName.get(packageName, classDescription.assertionClassName());

        TypeSpec.Builder assertClass = TypeSpec.classBuilder(assertClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ParameterizedTypeName.get(
                        ClassName.get(AbstractObjectAssert.class),
                        assertClassName,
                        ClassName.get(classDescription.clazz())));

        assertClass.addMethod(options.constructorGenerator().generate(classDescription));
        assertClass.addMethod(options.entryPointGenerator().generate(classDescription));

        classDescription.methods().forEach(it -> {
            assertClass.addMethods(options.commonMethodGenerator().generate(classDescription, it));
            options.methodGenerators(it.returnType())
                    .generate(classDescription, it)
                    .forEach(assertClass::addMethod);
        });

        return assertClass.build();
    }
}
