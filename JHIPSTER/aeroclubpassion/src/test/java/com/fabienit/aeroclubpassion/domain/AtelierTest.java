package com.fabienit.aeroclubpassion.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.aeroclubpassion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtelierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atelier.class);
        Atelier atelier1 = new Atelier();
        atelier1.setId(1L);
        Atelier atelier2 = new Atelier();
        atelier2.setId(atelier1.getId());
        assertThat(atelier1).isEqualTo(atelier2);
        atelier2.setId(2L);
        assertThat(atelier1).isNotEqualTo(atelier2);
        atelier1.setId(null);
        assertThat(atelier1).isNotEqualTo(atelier2);
    }
}
