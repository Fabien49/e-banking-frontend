package com.fabienit.aeroclubpassion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.aeroclubpassion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AeroclubDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AeroclubDTO.class);
        AeroclubDTO aeroclubDTO1 = new AeroclubDTO();
        aeroclubDTO1.setId(1L);
        AeroclubDTO aeroclubDTO2 = new AeroclubDTO();
        assertThat(aeroclubDTO1).isNotEqualTo(aeroclubDTO2);
        aeroclubDTO2.setId(aeroclubDTO1.getId());
        assertThat(aeroclubDTO1).isEqualTo(aeroclubDTO2);
        aeroclubDTO2.setId(2L);
        assertThat(aeroclubDTO1).isNotEqualTo(aeroclubDTO2);
        aeroclubDTO1.setId(null);
        assertThat(aeroclubDTO1).isNotEqualTo(aeroclubDTO2);
    }
}
