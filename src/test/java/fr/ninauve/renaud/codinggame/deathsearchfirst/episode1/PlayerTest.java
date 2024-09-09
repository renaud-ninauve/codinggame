package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PlayerTest {

    @Test
    void cut_nothing_when_no_gateway() {
        final int virusPosition = 1;

        final BobNet bobnet = Mockito.mock();
        when(bobnet.findNodesLinkedTo(virusPosition)).thenReturn(List.of(2));

        final Player player = new Player();

        final Optional<Link> actual = player.nextLinkToSever(bobnet, virusPosition);

        assertThat(actual).isEmpty();
    }

    @Test
    void cut_unique_link_to_gateway() {
        final int virusPosition = 1;

        final BobNet bobnet = Mockito.mock();
        when(bobnet.findNodesLinkedTo(virusPosition)).thenReturn(List.of(2, 3, 4));
        when(bobnet.isGateway(3)).thenReturn(true);

        final Player player = new Player();

        final Optional<Link> actual = player.nextLinkToSever(bobnet, virusPosition);

        assertThat(actual.get()).isEqualTo(new Link(1, 3));
    }
}
