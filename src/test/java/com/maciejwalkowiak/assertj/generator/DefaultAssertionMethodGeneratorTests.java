package com.maciejwalkowiak.assertj.generator;

import java.util.List;

import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAssertionMethodGeneratorTests {

    private final DefaultAssertionMethodGenerator generator = new DefaultAssertionMethodGenerator();
    private final ClassDescription classDescription = ClassDescription.of(Person.class);

    @Test
    void generatesHasMethod() {
        List<MethodSpec> methods = generator.generate(classDescription, classDescription.method("getFirstName"));

        assertThat(methods).anySatisfy(it -> {
            assertThat(it.name).isEqualTo("hasFirstName");
            assertThat(it.toString()).isEqualToIgnoringWhitespace("""
                public PersonAssert hasFirstName(java.lang.String arg) {
                    org.assertj.core.api.Assertions.assertThat(actual.getFirstName()).isEqualTo(arg);
                    return this;
                  }""");
        });
    }

    @Test
    void generatesSatisfiesMethod() {
        List<MethodSpec> methods = generator.generate(classDescription, classDescription.method("getFirstName"));

        assertThat(methods).anySatisfy(it -> {
            assertThat(it.name).isEqualTo("hasFirstNameSatisfying");
            assertThat(it.toString()).isEqualToIgnoringWhitespace("""
                public PersonAssert hasFirstNameSatisfying(java.util.function.Consumer<java.lang.String> arg) {
                     org.assertj.core.api.Assertions.assertThat(actual.getFirstName()).satisfies(arg);
                     return this;
                   }""");
        });
    }

    static class Person {
        private String firstName;

        public String getFirstName() {
            return firstName;
        }
    }

}
