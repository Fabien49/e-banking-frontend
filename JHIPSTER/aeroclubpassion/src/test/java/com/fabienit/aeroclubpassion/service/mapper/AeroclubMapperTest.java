package com.fabienit.aeroclubpassion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AeroclubMapperTest {

    private AeroclubMapper aeroclubMapper;

    @BeforeEach
    public void setUp() {
        aeroclubMapper = new AeroclubMapperImpl();
    }
}
