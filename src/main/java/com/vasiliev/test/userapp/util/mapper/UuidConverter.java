package com.vasiliev.test.userapp.util.mapper;

import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * The type Uuid converter.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Component
public class UuidConverter extends DozerConverter<String, UUID> {

    /**
     * Instantiates a new Uuid converter.
     */
    public UuidConverter() {
        super(String.class, UUID.class);
    }

    @Override
    public UUID convertTo(String s, UUID uuid) {
        if (s == null)
            return null;
        return UUID.fromString(s);
    }

    @Override
    public String convertFrom(UUID uuid, String s) {
        if (uuid == null)
            return null;
        return uuid.toString();
    }
}
