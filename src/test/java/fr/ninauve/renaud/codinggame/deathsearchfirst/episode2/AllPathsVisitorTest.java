package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Network.network;
import static org.assertj.core.api.Assertions.assertThat;

public class AllPathsVisitorTest {

    static class AllPathsVisitor implements Node.Visitor {
        private final List<Node> visited = new ArrayList<>();
        private final List<Path> paths = new ArrayList<>();
        private Path currentPath;
        private boolean isFirstStartAfterEnd = false;

        @Override
        public boolean start(Node node) {
            if (visited.contains(node)) {
                return false;
            }
            visited.add(node);
            if (currentPath == null) {
                currentPath = new Path(List.of());
            }
            if (isFirstStartAfterEnd) {
                isFirstStartAfterEnd = false;
                paths.add(currentPath);
                currentPath = currentPath.withoutLast();
            }
            currentPath = currentPath.withNewNode(node);
            return true;
        }

        @Override
        public void end(Node node) {
            isFirstStartAfterEnd = true;
            if (node == visited.get(0)) {
                paths.add(currentPath);
            }
        }
    }

    @Test
    void test() {
        Network network = network().link(11, 12)
                .link(12, 13)
                .link(13, 14)
                .link(13, 15)
                .link(15, 16)
                .link(15, 17)
                .build();

        Node node1 = network.getNode(11);
        AllPathsVisitor visitor = new AllPathsVisitor();
        node1.visitDepthFirst(visitor, Comparator.comparing(Node::value));
        List<List<Integer>> actual = visitor.paths.stream()
                .map(path -> path.stream().map(Node::value).toList())
                .toList();
        assertThat(actual).contains(
                List.of(11, 12, 13, 14),
                List.of(11, 12, 13, 15, 16),
                List.of(11, 12, 13, 15, 17));
    }
}
