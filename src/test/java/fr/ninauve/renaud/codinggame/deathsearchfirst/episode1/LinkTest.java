package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkTest {

    @Test
    void equals_reversed_link() {
        assertThat(new Link(1, 2)).isEqualTo(new Link(2, 1));
    }
}
