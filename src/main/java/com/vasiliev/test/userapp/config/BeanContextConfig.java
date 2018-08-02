package com.vasiliev.test.userapp.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Collections;

/**
 * The type Bean context config.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Configuration
public class BeanContextConfig {

    private static final String PHASE_DIFF_FILE_PATH = "mapper/user-to-dto.xml";

    /**
     * Dozer mapper mapper.
     *
     * @return the mapper
     */
    @Bean("userMapper")
    public Mapper dozerMapper() {
        return new DozerBeanMapper(Collections.singletonList(PHASE_DIFF_FILE_PATH));
    }

    /**
     * Message source message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
}
