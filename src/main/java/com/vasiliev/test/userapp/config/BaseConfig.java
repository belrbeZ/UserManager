package com.vasiliev.test.userapp.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class BaseConfig {

    private static final String PHASE_DIFF_FILE_PATH = "mapper/user-to-dto.xml";

    @Bean("userMapper")
    public Mapper dozerMapper() {
        return new DozerBeanMapper(Collections.singletonList(PHASE_DIFF_FILE_PATH));
    }
}
