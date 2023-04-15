package com.maciejwalkowiak.assertj.generator;

import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEntryPointGeneratorTests {
    private final DefaultEntryPointMethodGenerator generator = new DefaultEntryPointMethodGenerator();

    @Test
    void generatesEntrypoint() {
        ClassDescription classDescription = ClassDescription.of(Person.class);

        MethodSpec constructor = generator.generate(classDescription);

        assertThat(constructor.toString()).isEqualToIgnoringWhitespace("""
                public static PersonAssert assertThat(
                       com.maciejwalkowiak.assertj.generator.DefaultEntryPointGeneratorTests.Person it) {
                     return new PersonAssert(it);
                   }""");
    }

    static class Person {
    }

}
