package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShortestPath {

    public static Optional<Path> find(Node start, List<Node> destinations) {
        Result result = new Result();
        find(result, new Path(List.of(start)), destinations);
        return result.shortestPath;
    }

    private static void find(Result result, Path currentPath, List<Node> destinations) {
        if (result.shortestPath.isPresent() && currentPath.compareTo(result.shortestPath.get()) >= 0) {
            return;
        }
        Node currentNode = currentPath.node(-1);
        if (destinations.contains(currentNode)) {
            result.shortestPath = Optional.of(currentPath);
            return;
        }

        for (Node target : currentNode.neighbours().toList()) {
            if (currentPath.contains(target)) {
                continue;
            }
            final Path newPath = currentPath.withNewNode(target);
            find(result, newPath, destinations);
        }
    }

    private static class Result {
        private Optional<Path> shortestPath = Optional.empty();
    }
}
