package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    void cut_unique_link_to_gateway() {
        final BobNet bobnet = BobNet.with()
                .linkBetween(1, 2)
                .gatewayAt(2)
                .build();

        final int virusPosition = 1;
        final Player player = new Player();

        final Link actual = player.nextLinkToSever(bobnet, virusPosition);
        assertThat(actual).isEqualTo(new Link(1, 2));
    }

}
