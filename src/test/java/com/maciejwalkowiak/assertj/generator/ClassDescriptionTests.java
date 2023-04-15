package com.maciejwalkowiak.assertj.generator;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassDescriptionTests {

    @Test
    void resolvesClassProperties() {
        ClassDescription classDescription = ClassDescription.of(Person.class);
        assertThat(classDescription.assertionClassName()).isEqualTo("PersonAssert");
        assertThat(classDescription.packageName()).isEqualTo("com.maciejwalkowiak.assertj.generator");
        assertThat(classDescription.clazz()).isEqualTo(Person.class);
        assertThat(classDescription.methods()).hasSize(3);
    }

    @Test
    void resolvesMethodProperties() {
        ClassDescription classDescription = ClassDescription.of(Person.class);
        assertThat(classDescription.methods()).anySatisfy(method -> {
            assertThat(method.methodName()).isEqualTo("getLastName");
            assertThat(method.fieldName()).isEqualTo("lastName");
            assertThat(method.returnType()).isEqualTo(String.class);
        });

        assertThat(classDescription.methods()).anySatisfy(method -> {
            assertThat(method.methodName()).isEqualTo("getFirstName");
            assertThat(method.fieldName()).isEqualTo("firstName");
            assertThat(method.returnType()).isEqualTo(String.class);
        });

        assertThat(classDescription.methods()).anySatisfy(method -> {
            assertThat(method.methodName()).isEqualTo("getAge");
            assertThat(method.fieldName()).isEqualTo("age");
            assertThat(method.returnType()).isEqualTo(Optional.class);
            assertThat(method.genericReturnType(0)).isEqualTo(Integer.class);
        });
    }

    static class Person {
        private String firstName;
        private String lastName;
        private Optional<Integer> age;

        Optional<Integer> getAge() {
            return age;
        }

        String getFirstName() {
            return firstName;
        }

        String getLastName() {
            return lastName;
        }
    }
}
