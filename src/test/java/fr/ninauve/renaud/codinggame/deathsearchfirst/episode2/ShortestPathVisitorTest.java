package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathVisitorTest {
    private static final int START = 1_000_000;
    private static final int DESTINATION1 = 1_999_999;
    private static final int DESTINATION2 = 2_999_999;

    static class ShortestPathVisitor implements Node.Visitor {
        private final List<Node> destinations;
        private Path shortest;
        private Path currentPath;

        ShortestPathVisitor(List<Node> destinations) {
            this.destinations = destinations;
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
            if (destinations.contains(node)) {
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
                                .link(2, DESTINATION1)
                                .build(),
                        List.of(DESTINATION1),
                        List.of(START, 2, DESTINATION1)),

                Arguments.of(
                        network()
                                .link(START, 2)
                                .link(2, 3)
                                .link(3, 4)
                                .link(4, 5)
                                .link(5, DESTINATION1)
                                .link(2, 6)
                                .link(6, DESTINATION1)
                                .build(),
                        List.of(DESTINATION1),
                        List.of(START, 2, 6, DESTINATION1)
                ),

                Arguments.of(
                        network()
                                .link(START, 2)
                                .link(2, 3)
                                .link(3, DESTINATION1)
                                .link(START, 4)
                                .link(4, DESTINATION2)
                                .build(),
                        List.of(DESTINATION1, DESTINATION2),
                        List.of(START, 4, DESTINATION2))
        );
    }

    @ParameterizedTest
    @MethodSource
    void find_shortest(Network network, List<Integer> destinations, List<Integer> expected) {
        Node start = network.getNode(START);
        List<Node> destinationNodes = destinations.stream()
                .map(network::getNode)
                .toList();
        ShortestPathVisitor visitor = new ShortestPathVisitor(destinationNodes);
        start.visitDepthFirst(visitor, Comparator.comparing(Node::value));
        List<Integer> actual = visitor.shortest.stream()
                .map(Node::value)
                .toList();
        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
