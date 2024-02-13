package com.fabienit.rechercher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fabienit.rechercher.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PiloteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pilote.class);
        Pilote pilote1 = new Pilote();
        pilote1.setId(1L);
        Pilote pilote2 = new Pilote();
        pilote2.setId(pilote1.getId());
        assertThat(pilote1).isEqualTo(pilote2);
        pilote2.setId(2L);
        assertThat(pilote1).isNotEqualTo(pilote2);
        pilote1.setId(null);
        assertThat(pilote1).isNotEqualTo(pilote2);
    }
}
