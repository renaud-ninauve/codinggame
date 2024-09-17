package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathTest {
    private static final int START = 1_000_000;
    private static final int DESTINATION1 = 1_999_999;
    private static final int DESTINATION2 = 2_999_999;

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
        Optional<Path> actual = ShortestPath.find(start, destinationNodes);
        List<Integer> actualNodes = actual.get().stream().map(Node::value).toList();
        assertThat(actualNodes).containsExactlyElementsOf(expected);
    }
}
