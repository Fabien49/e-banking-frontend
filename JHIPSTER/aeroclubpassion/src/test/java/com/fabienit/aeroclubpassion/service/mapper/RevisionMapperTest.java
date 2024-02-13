package com.fabienit.aeroclubpassion.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RevisionMapperTest {

    private RevisionMapper revisionMapper;

    @BeforeEach
    public void setUp() {
        revisionMapper = new RevisionMapperImpl();
    }
}
