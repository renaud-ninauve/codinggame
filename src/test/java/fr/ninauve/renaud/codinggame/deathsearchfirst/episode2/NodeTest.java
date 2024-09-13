package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Node.node;
import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(
                        "return_true_when_same_value",
                        node(1, node(2)),
                        node(1, node(2)),
                        true
                ),
                Arguments.of(
                        "return_true_when_same_value_different_neighbours",
                        node(1, node(2)),
                        node(1, node(3)),
                        true
                ),
                Arguments.of(
                        "return_false_when_different_value",
                        node(1, node(2)),
                        node(2, node(2)),
                        false
                ));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("source")
    void equals_should(String scenario, Node node, Node other, boolean expected) {
        if (expected) {
            assertThat(node).isEqualTo(other);
            assertThat(node.hashCode()).isEqualTo(other.hashCode());
        } else {
            assertThat(node).isNotEqualTo(other);
        }
    }

    static class NodeListVisitor implements Node.Visitor {
        private final List<Node> visited = new ArrayList<>();
        private final List<String> history = new ArrayList<>();

        @Override
        public boolean start(Node node) {
            if (visited.contains(node)) {
                return false;
            }
            visited.add(node);
            history.add("START " + node.value());
            return true;
        }

        @Override
        public void end(Node node) {
            history.add("END " + node.value());
        }

        List<Node> getNodes() {
            return visited;
        }

        List<String> getHistory() {
            return history;
        }
    }

    @Test
    void should_visit_depth_first() {
        Network network = network().link(11, 12)
                .link(12, 13)
                .link(13, 14)
                .link(13, 15)
                .link(15, 16)
                .link(15, 17)
                .build();

        Node node1 = network.getNode(11);
        NodeListVisitor nodeVisitor = new NodeListVisitor();
        node1.visitDepthFirst(nodeVisitor, Comparator.comparing(Node::value));

        List<String> actualHistory = nodeVisitor.getHistory();

        assertThat(actualHistory).containsExactly(
                "START 11",
                "START 12",
                "START 13",
                "START 14",
                "END 14",
                "START 15",
                "START 16",
                "END 16",
                "START 17",
                "END 17",
                "END 15",
                "END 13",
                "END 12",
                "END 11");
    }

    @Test
    void should_visit_depth_first_in_order() {
        Network network = network().link(11, 12)
                .link(12, 13)
                .link(13, 14)
                .link(13, 15)
                .link(15, 16)
                .link(15, 17)
                .build();

        Node node1 = network.getNode(11);
        NodeListVisitor nodeVisitor = new NodeListVisitor();
        node1.visitDepthFirst(nodeVisitor, Comparator.comparing(Node::value).reversed());

        List<String> actualHistory = nodeVisitor.getHistory();

        assertThat(actualHistory).containsExactly(
                "START 11",
                "START 12",
                "START 13",
                "START 15",
                "START 17",
                "END 17",
                "START 16",
                "END 16",
                "END 15",
                "START 14",
                "END 14",
                "END 13",
                "END 12",
                "END 11");
    }
}