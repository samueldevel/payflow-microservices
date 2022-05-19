package com.samueldev.project.user.util.test.setup;

import org.assertj.core.api.Assertions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class AssertUtils {

    public <T> void assertFields(T object, T comparedObject) {
        if (object.getClass() != comparedObject.getClass()) {
            throw new IllegalArgumentException("compared Object is not the same class, please set two same class objects");
        }

        try {
            assertIfIsNotNullOrEmptyFields(object);
            assertFieldIsEqual(object, comparedObject);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private <T> void assertIfIsNotNullOrEmptyFields(T object) throws IllegalAccessException {
        List<Object> fieldsValue = getFieldsValue(object);

        for (Object value : fieldsValue) {
            Assertions.assertThat(object)
                    .isNotNull();

            if (value instanceof String) {
                assertStringNotNullOrEmpty((String) value);
            }

        }
    }

    public <T> void assertFieldIsEqual(T userToBeTest, T userToBeCompared) throws IllegalAccessException {
        List<Object> userTest = getFieldsValue(userToBeTest);
        List<Object> userCompared = getFieldsValue(userToBeCompared);

        Assertions.assertThat(userTest)
                .containsAll(userCompared);
    }

    public void assertStringNotNullOrEmpty(String stringToBeTest) {
        Assertions.assertThat(stringToBeTest)
                .isNotNull()
                .isNotEmpty();
    }

    private static <T> List<Object> getFieldsValue(T user) throws IllegalAccessException {
        List<Object> values = new ArrayList<>();

        Class<?> aClass = user.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            values.add(field.get(user));
        }

        return values;
    }
}
