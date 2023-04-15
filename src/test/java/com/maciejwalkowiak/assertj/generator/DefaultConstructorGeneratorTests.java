package com.maciejwalkowiak.assertj.generator;

import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultConstructorGeneratorTests {
    private final DefaultConstructorGenerator generator = new DefaultConstructorGenerator();

    @Test
    void generatesConstructor() {
        ClassDescription classDescription = ClassDescription.of(Person.class);

        MethodSpec constructor = generator.generate(classDescription);

        assertThat(constructor.toString()).isEqualToIgnoringWhitespace("""
                public Constructor(
                      com.maciejwalkowiak.assertj.generator.DefaultConstructorGeneratorTests.Person it) {
                    super(it, PersonAssert.class);
                  }""");
    }

    static class Person {
    }

}
