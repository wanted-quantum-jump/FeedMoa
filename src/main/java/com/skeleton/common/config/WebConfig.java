package com.skeleton.common.config;

import com.skeleton.common.util.EnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ConverterFactory converterFactory = new EnumConverterFactory();
        registry.addConverterFactory(converterFactory);
    }
}
