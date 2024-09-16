package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class Player2Test {

    @Test
    void cut_direct_link_to_gateways() {
        Network network = TestData.networkExample()
                .gateway(5)
                .build();

        Node _0 = network.getNode(0);

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        Node _5 = network.getNode(5);
        assertThat(actual).hasValue(new Link(_0, _5));
    }

    @Test
    void cut_nearest_when_no_node_linked_to_several_gateways() {
        Network network = TestData.networkExample().build();
        Node _0 = network.getNode(0);
        Node _6 = network.getNode(6);
        Node _7 = network.getNode(7);
        network.deleteLink(_6, _7);

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        Node _5 = network.getNode(5);
        Node _9 = network.getNode(9);
        assertThat(actual).hasValue(new Link(_5, _9));
    }

    @Test
    void cut_nearest_node_linked_to_2_gateways() {
        Network network = TestData.networkExample().build();
        Node _0 = network.getNode(0);
        Node _6 = network.getNode(6);
        Node _7 = network.getNode(7);
        network.deleteLink(_6, _7);

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        Node _5 = network.getNode(5);
        Node _9 = network.getNode(9);
        assertThat(actual).hasValue(new Link(_5, _9));
    }
}