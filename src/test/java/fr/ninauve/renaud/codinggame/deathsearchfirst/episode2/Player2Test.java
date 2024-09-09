package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import fr.ninauve.renaud.codinggame.deathsearchfirst.BobNet;
import fr.ninauve.renaud.codinggame.deathsearchfirst.Link;
import fr.ninauve.renaud.codinggame.deathsearchfirst.episode1.Player1;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class Player2Test {

    @Test
    void cut_unique_link_to_gateway() {
        final BobNet bobnet = BobNet.with()
                .linkBetween(1, 2)
                .gatewayAt(2)
                .build();

        final int virusPosition = 1;
        final Player2 player2 = new Player2();

        final Optional<Link> actual = player2.nextLinkToSever(bobnet, virusPosition);
        assertThat(actual.get()).isEqualTo(new Link(1, 2));
    }

    @Test
    void cut_shortest_link_to_gateway() {
        final int gateway = 999;

        final BobNet bobnet = BobNet.with()
                .linkBetween(1, 2)
                .linkBetween(2, 21)
                .linkBetween(21, 22)
                .linkBetween(22, gateway)

                .linkBetween(1, 3)
                .linkBetween(3, 31)
                .linkBetween(31, gateway)

                .linkBetween(3, 301)

                .linkBetween(1, 4)
                .linkBetween(4, 41)
                .linkBetween(41, 42)
                .linkBetween(42, gateway)

                .gatewayAt(gateway)
                .build();

        final int virusPosition = 1;
        final Player2 player2 = new Player2();

        final Optional<Link> actual = player2.nextLinkToSever(bobnet, virusPosition);
        assertThat(actual.get()).isEqualTo(new Link(22, gateway));
    }
}