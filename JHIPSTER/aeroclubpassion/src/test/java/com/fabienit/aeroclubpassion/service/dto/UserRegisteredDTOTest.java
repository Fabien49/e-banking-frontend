package com.fabienit.aeroclubpassion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.aeroclubpassion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRegisteredDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRegisteredDTO.class);
        UserRegisteredDTO userRegisteredDTO1 = new UserRegisteredDTO();
        userRegisteredDTO1.setId(1L);
        UserRegisteredDTO userRegisteredDTO2 = new UserRegisteredDTO();
        assertThat(userRegisteredDTO1).isNotEqualTo(userRegisteredDTO2);
        userRegisteredDTO2.setId(userRegisteredDTO1.getId());
        assertThat(userRegisteredDTO1).isEqualTo(userRegisteredDTO2);
        userRegisteredDTO2.setId(2L);
        assertThat(userRegisteredDTO1).isNotEqualTo(userRegisteredDTO2);
        userRegisteredDTO1.setId(null);
        assertThat(userRegisteredDTO1).isNotEqualTo(userRegisteredDTO2);
    }
}
