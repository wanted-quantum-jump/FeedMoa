package com.skeleton.common.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EnumConverterFactory implements ConverterFactory<String, Enum<? extends EnumMapperType>> {
    @Override
    public <T extends Enum<? extends EnumMapperType>> Converter<String, T> getConverter(Class<T> targetType) {
        if (EnumMapperType.class.isAssignableFrom(targetType)) {
            return new StringToEnumConverter<>(targetType);
        }
        return null;
    }

    private static class StringToEnumConverter<T extends Enum<? extends EnumMapperType>> implements Converter<String, T> {
        private final Map<String, T> map;

        StringToEnumConverter(Class<T> enumType) {
            T[] enumConstants = enumType.getEnumConstants();
            map = Arrays.stream(enumConstants)
                    .collect(Collectors.toMap(
                            enumConstant -> ((EnumMapperType) enumConstant).getValue(),
                            Function.identity()
                    ));
        }

        @Override
        public T convert(String source) {
            return map.get(source);
        }
    }
}