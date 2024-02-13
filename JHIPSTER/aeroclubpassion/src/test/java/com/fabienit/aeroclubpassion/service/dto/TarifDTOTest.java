package com.fabienit.aeroclubpassion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.aeroclubpassion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarifDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifDTO.class);
        TarifDTO tarifDTO1 = new TarifDTO();
        tarifDTO1.setId(1L);
        TarifDTO tarifDTO2 = new TarifDTO();
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO2.setId(tarifDTO1.getId());
        assertThat(tarifDTO1).isEqualTo(tarifDTO2);
        tarifDTO2.setId(2L);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO1.setId(null);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
    }
}
