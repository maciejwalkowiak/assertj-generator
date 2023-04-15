package com.maciejwalkowiak.assertj.generator;

import java.util.List;
import java.util.Optional;

import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalAssertionMethodGeneratorTests {

    private final OptionalAssertionMethodGenerator generator = new OptionalAssertionMethodGenerator();
    private final ClassDescription classDescription = ClassDescription.of(Person.class);

    @Test
    void generatesHasMethod() {
        List<MethodSpec> methods = generator.generate(classDescription, classDescription.method("getFirstName"));

        assertThat(methods).anySatisfy(it -> {
            assertThat(it.name).isEqualTo("hasFirstName");
            assertThat(it.toString()).isEqualToIgnoringWhitespace("""
                    public PersonAssert hasFirstName(java.lang.String arg) {
                         org.assertj.core.api.Assertions.assertThat(actual.getFirstName()).hasValue(arg);
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
                          org.assertj.core.api.Assertions.assertThat(actual.getFirstName()).hasValueSatisfying(arg);
                          return this;
                        }""");
        });
    }

    static class Person {
        private Optional<String> firstName;

        public Optional<String> getFirstName() {
            return firstName;
        }
    }

}
