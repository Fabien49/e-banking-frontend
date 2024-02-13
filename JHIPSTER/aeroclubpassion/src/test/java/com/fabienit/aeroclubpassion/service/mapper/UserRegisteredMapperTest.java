package com.fabienit.aeroclubpassion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRegisteredMapperTest {

    private UserRegisteredMapper userRegisteredMapper;

    @BeforeEach
    public void setUp() {
        userRegisteredMapper = new UserRegisteredMapperImpl();
    }
}
