package com.fabienit.aeroclubpassion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TarifMapperTest {

    private TarifMapper tarifMapper;

    @BeforeEach
    public void setUp() {
        tarifMapper = new TarifMapperImpl();
    }
}
