package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathVisitorTest {
    private static final int START = 1_000_000;
    private static final int DESTINATION = 9_999_999;

    static class ShortestPathVisitor implements Node.Visitor {
        private final Node destination;
        private Path shortest;
        private Path currentPath;

        ShortestPathVisitor(Node destination) {
            this.destination = destination;
        }

        @Override
        public boolean start(Node node) {
            if (currentPath == null) {
                currentPath = new Path(List.of());
            }
            if (currentPath.contains(node)) {
                return false;
            }
            currentPath = currentPath.withNewNode(node);
            if (node == destination) {
                if (shortest == null || currentPath.compareTo(shortest) < 0) {
                    shortest = currentPath;
                }
            }
            return shortest == null || currentPath.compareTo(shortest) < 0;
        }

        @Override
        public void end(Node node) {
            if (node != currentPath.node(-1)) {
                currentPath = currentPath.withoutLast().withoutLast();
            } else {
                currentPath = currentPath.withoutLast();
            }
        }
    }

    static Stream<Arguments> find_shortest() {
        return Stream.of(Arguments.of(
                        network()
                                .link(START, 2)
                                .link(2, DESTINATION)
                                .build(),
                        List.of(START, 2, DESTINATION)),

                Arguments.of(
                        network()
                                .link(START, 2)
                                .link(2, 3)
                                .link(3, 4)
                                .link(4, 5)
                                .link(5, DESTINATION)
                                .link(2, 6)
                                .link(6, DESTINATION)
                                .build(),
                        List.of(START, 2, 6, DESTINATION)
                ));
    }

    @ParameterizedTest
    @MethodSource
    void find_shortest(Network network, List<Integer> expected) {
        Node start = network.getNode(START);
        Node destination = network.getNode(DESTINATION);
        ShortestPathVisitor visitor = new ShortestPathVisitor(destination);
        start.visitDepthFirst(visitor, Comparator.comparing(Node::value));
        List<Integer> actual = visitor.shortest.stream()
                .map(Node::value)
                .toList();
        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
