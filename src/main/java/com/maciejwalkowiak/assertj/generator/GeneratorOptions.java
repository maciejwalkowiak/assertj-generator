package com.maciejwalkowiak.assertj.generator;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

public class GeneratorOptions {
    private AssertionMethodGenerator defaultMethodGenerator = new DefaultAssertionMethodGenerator();
    private AssertionMethodGenerator commonMethodGenerator = new CommonAssertionMethodGenerator();
    private ConstructorGenerator constructorGenerator = new DefaultConstructorGenerator();
    private EntryPointMethodGenerator entryPointMethodGenerator = new DefaultEntryPointMethodGenerator();
    private Map<Class<?>, AssertionMethodGenerator> methodGenerators = new HashMap<>();
    @Nullable
    private String entrypointPackage;
    private Path targetDirectory;

    GeneratorOptions() {
        methodGenerators.put(Optional.class, new OptionalAssertionMethodGenerator());
        methodGenerators.put(List.class, new CollectionAssertionMethodGenerator());
        methodGenerators.put(Set.class, new CollectionAssertionMethodGenerator());
        methodGenerators.put(Collection.class, new CollectionAssertionMethodGenerator());
    }

    @Nullable
    String entrypointPackage() {
        return entrypointPackage;
    }

    GeneratorOptions entrypointPackage(@Nullable String entrypointPackage) {
        this.entrypointPackage = entrypointPackage;
        return this;
    }

    Path targetDirectory() {
        return targetDirectory;
    }

    GeneratorOptions targetDirectory(String targetDirectory) {
        return targetDirectory(Path.of(targetDirectory));
    }

    GeneratorOptions targetDirectory(Path targetDirectory) {
        this.targetDirectory = targetDirectory;
        return this;
    }

    AssertionMethodGenerator defaultMethodGenerator() {
        return defaultMethodGenerator;
    }

    AssertionMethodGenerator commonMethodGenerator() {
        return commonMethodGenerator;
    }

    ConstructorGenerator constructorGenerator() {
        return constructorGenerator;
    }

    EntryPointMethodGenerator entryPointGenerator() {
        return entryPointMethodGenerator;
    }

    AssertionMethodGenerator methodGenerators(Class<?> clazz) {
        return methodGenerators.getOrDefault(clazz, defaultMethodGenerator());
    }
}
