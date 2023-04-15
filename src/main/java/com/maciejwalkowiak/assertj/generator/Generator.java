package com.maciejwalkowiak.assertj.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.assertj.core.api.Assertions;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

class Generator {

    private final GeneratorOptions options;
    private final Set<Class<?>> classes = new HashSet<>();
    private final Set<String> packages = new HashSet<>();

    Generator(GeneratorOptions options) {
        this.options = options;
    }

    Generator addClass(Class<?> clazz) {
        this.classes.add(clazz);
        return this;
    }

    Generator addPackage(String packageName) {
        this.packages.add(packageName);
        return this;
    }

    GenerateResult generate() {
        String lowerCase = options.entrypointPackage().toLowerCase();
        Set<Class<?>> allClasses = new HashSet<>();
        allClasses.addAll(this.packages.stream()
                .flatMap(Generator::findClassesInPackage)
                .toList());
        allClasses.addAll(classes);

        ClassGenerator classGenerator = new ClassGenerator();

        List<GeneratedClass> list = allClasses.stream()
                .map(ClassDescription::of)
                .map(cd -> new GeneratedClass(cd, classGenerator.generate(cd, options)))
                .toList();

        TypeSpec.Builder assertClass = TypeSpec.classBuilder("Assertions")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(Assertions.class);

        List<MethodSpec> assertThatMethods = list.stream().map(generatedClass -> {
            MethodSpec main = MethodSpec.methodBuilder("assertThat")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ClassName.get(generatedClass.classDescription().packageName(),
                            generatedClass.classDescription().assertionClassName()))
                    .addParameter(generatedClass.classDescription().clazz(), "arg")
                    .addStatement("return new $L(arg)", ClassName.get(generatedClass.classDescription().packageName(),
                            generatedClass.classDescription().assertionClassName()))
                    .build();
            return main;
        }).toList();

        assertClass.addMethods(assertThatMethods);

        return new GenerateResult(list, assertClass.build());
    }

    private static Stream<Class<?>> findClassesInPackage(String p) {
        return new Reflections(p, Scanners.SubTypes.filterResultsBy(c -> true)) // include Object class
                .getSubTypesOf(Object.class)
                .stream();
    }

    GenerateResult generateFiles() {
        GenerateResult result = this.generate();

        writeClassToFile(options.entrypointPackage(), "Assertions", result.entryPoint());
        result.generatedClasses().forEach(this::writeClassToFile);

        return result;
    }

    void writeClassToFile(GeneratedClass generatedClass) {
        writeClassToFile(generatedClass.classDescription().packageName(),
                generatedClass.classDescription().assertionClassName(), generatedClass.typeSpec());
    }

    void writeClassToFile(String packageName, String className, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();
        Path directory = options.targetDirectory().resolve(Path.of(packageName.replace(".", "/")));

        try {
            Files.createDirectories(directory);
            Files.writeString(directory.resolve(className + ".java"), javaFile.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
