package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import fr.ninauve.renaud.codinggame.deathsearchfirst.BobNet;
import fr.ninauve.renaud.codinggame.deathsearchfirst.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player1 {
    public Optional<Link> nextLinkToSever(BobNet bobnet, int virusPosition) {
        ShortestPath shortestPath = new ShortestPath();
        shortestPathToGateway(shortestPath, bobnet, List.of(virusPosition));

        return shortestPath.found() ? Optional.of(new Link(shortestPath.value.get(0), shortestPath.value.get(1))) : Optional.empty();
    }

    private void shortestPathToGateway(ShortestPath shortestPath, BobNet bobNet, List<Integer> currentPath) {
        if (shortestPath.found() && currentPath.size() >= shortestPath.size()) {
            return;
        }
        final Integer currentPosition = currentPath.get(currentPath.size() - 1);
        if (bobNet.isGateway(currentPosition)) {
            shortestPath.value = currentPath;
            return;
        }

        final List<Integer> targets = bobNet.findNodesLinkedTo(currentPosition);
        for (int target : targets) {
            if (currentPath.contains(target)) {
                continue;
            }
            final List<Integer> newPath = new ArrayList<>();
            newPath.addAll(currentPath);
            newPath.add(target);
            shortestPathToGateway(shortestPath, bobNet, newPath);
        }
    }

    private static class ShortestPath {
        private List<Integer> value;

        private boolean found() {
            return value != null;
        }

        private int size() {
            return value.size();
        }
    }
}
