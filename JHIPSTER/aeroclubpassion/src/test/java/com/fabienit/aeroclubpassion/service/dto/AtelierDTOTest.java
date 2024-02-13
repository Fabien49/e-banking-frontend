package com.fabienit.aeroclubpassion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.aeroclubpassion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtelierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtelierDTO.class);
        AtelierDTO atelierDTO1 = new AtelierDTO();
        atelierDTO1.setId(1L);
        AtelierDTO atelierDTO2 = new AtelierDTO();
        assertThat(atelierDTO1).isNotEqualTo(atelierDTO2);
        atelierDTO2.setId(atelierDTO1.getId());
        assertThat(atelierDTO1).isEqualTo(atelierDTO2);
        atelierDTO2.setId(2L);
        assertThat(atelierDTO1).isNotEqualTo(atelierDTO2);
        atelierDTO1.setId(null);
        assertThat(atelierDTO1).isNotEqualTo(atelierDTO2);
    }
}
