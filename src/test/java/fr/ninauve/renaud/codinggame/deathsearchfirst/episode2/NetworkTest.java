package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static org.assertj.core.api.Assertions.assertThat;

class NetworkTest {

    @Nested
    class BuilderShould {
        @Test
        void create_empty_network() {
            Network actual = network().build();
            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void create_bi_link() {
            Network actual = network()
                    .link(1, 2)
                    .link(1, 3)
                    .build();

            Node actualNode1 = actual.getNode(1);
            assertThat(actualNode1.value()).isEqualTo(1);

            Node actualNode2 = actual.getNode(2);
            assertThat(actualNode2.value()).isEqualTo(2);

            Node actualNode3 = actual.getNode(3);
            assertThat(actualNode3.value()).isEqualTo(3);

            List<Node> actualNeighbours1 = actualNode1.neighbours().toList();
            assertThat(actualNeighbours1)
                    .hasSize(2)
                    .anyMatch(n -> n == actualNode2)
                    .anyMatch(n -> n == actualNode3);

            List<Node> actualNeighbours2 = actualNode2.neighbours().toList();
            assertThat(actualNeighbours2)
                    .hasSize(1)
                    .anyMatch(n -> n == actualNode1);

            List<Node> actualNeighbours3 = actualNode3.neighbours().toList();
            assertThat(actualNeighbours3)
                    .hasSize(1)
                    .anyMatch(n -> n == actualNode1);
        }

        @Test
        void create_gateways() {
            Network actual = network()
                    .link(1, 2)
                    .link(1, 3)
                    .gateway(999)
                    .build();

            assertThat(actual.getNode(999).isGateway())
                    .isTrue();
        }
    }

    @Test
    void delete_link() {
        Network network = network()
                .link(1, 2)
                .link(1, 3)
                .gateway(999)
                .build();

        Node node1 = network.getNode(1);
        Node node2 = network.getNode(2);
        Node node3 = network.getNode(3);

        network.deleteLink(node1, node2);

        List<Node> neighbours1 = node1.neighbours().toList();
        assertThat(neighbours1)
                .hasSize(1)
                .anyMatch(n -> n == node3);

        List<Node> neighbours2 = node2.neighbours().toList();
        assertThat(neighbours2).isEmpty();

        List<Node> neighbours3 = node3.neighbours().toList();
        assertThat(neighbours3)
                .hasSize(1)
                .anyMatch(n -> n == node1);
    }
}