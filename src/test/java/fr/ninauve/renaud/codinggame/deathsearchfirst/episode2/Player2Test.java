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
        Node _3 = network.getNode(3);
        Node _4 = network.getNode(4);
        Node _6 = network.getNode(6);
        Node _7 = network.getNode(7);
        network.deleteLink(_3, _4);
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

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        Node _3 = network.getNode(3);
        Node _4 = network.getNode(4);
        assertThat(actual).hasValue(new Link(_4, _3));
    }

    @Test
    void cut_nothing_when_no_path_to_gateway_double() {
        Network network = TestData.networkExample().build();;
        Node _0 = network.getNode(0);
        Node _1 = network.getNode(1);
        Node _2 = network.getNode(2);
        Node _3 = network.getNode(3);
        Node _4 = network.getNode(4);
        Node _5 = network.getNode(5);
        Node _6 = network.getNode(6);
        Node _7 = network.getNode(7);
        Node _8 = network.getNode(8);
        Node _9 = network.getNode(9);
        Node _10 = network.getNode(10);
        Node _11 = network.getNode(11);
        Node _12 = network.getNode(12);
        Node _13 = network.getNode(13);

        // isolate agent
        network.deleteLink(_5, _9);
        network.deleteLink(_9, _10);
        network.deleteLink(_1, _2);
        network.deleteLink(_10, _11);

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        assertThat(actual).isEmpty();
    }

    @Test
    void cut_nothing_when_no_path_to_gateway_single() {
        Network network = TestData.networkExample().build();;
        Node _0 = network.getNode(0);
        Node _1 = network.getNode(1);
        Node _2 = network.getNode(2);
        Node _3 = network.getNode(3);
        Node _4 = network.getNode(4);
        Node _5 = network.getNode(5);
        Node _6 = network.getNode(6);
        Node _7 = network.getNode(7);
        Node _8 = network.getNode(8);
        Node _9 = network.getNode(9);
        Node _10 = network.getNode(10);
        Node _11 = network.getNode(11);
        Node _12 = network.getNode(12);
        Node _13 = network.getNode(13);

        // isolate agent
        network.deleteLink(_5, _9);
        network.deleteLink(_9, _10);
        network.deleteLink(_1, _2);
        network.deleteLink(_10, _11);

        // cut double nodes
        network.deleteLink(_3, _4);
        network.deleteLink(_6, _7);

        Optional<Link> actual = new Player2().nextLinkToCut(network, _0);

        assertThat(actual).isEmpty();
    }
}