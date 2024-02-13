package com.fabienit.aeroclubpassion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtelierMapperTest {

    private AtelierMapper atelierMapper;

    @BeforeEach
    public void setUp() {
        atelierMapper = new AtelierMapperImpl();
    }
}
