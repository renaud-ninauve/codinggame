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
        private final List<Node> nodes = new ArrayList<>();

        @Override
        public boolean visit(Node node) {
            if (nodes.contains(node)) {
                return false;
            }
            nodes.add(node);
            return true;
        }

        public List<Node> getNodes() {
            return nodes;
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

        List<Integer> actual = nodeVisitor.getNodes().stream()
                .map(Node::value)
                .toList();

        assertThat(actual).containsExactly(11, 12, 13, 14, 15, 16, 17);
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

        List<Integer> actual = nodeVisitor.getNodes().stream()
                .map(Node::value)
                .toList();

        assertThat(actual).containsExactly(11, 12, 13, 15, 17, 16, 14);
    }
}