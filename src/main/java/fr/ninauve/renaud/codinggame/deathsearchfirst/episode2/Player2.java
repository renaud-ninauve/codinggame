package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import fr.ninauve.renaud.codinggame.deathsearchfirst.BobNet;
import fr.ninauve.renaud.codinggame.deathsearchfirst.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player2 {
    public Optional<Link> nextLinkToSever(BobNet bobnet, int virusPosition) {
        ShortestPath shortestPath = new ShortestPath();
        shortestPathToGateway(shortestPath, bobnet, List.of(virusPosition));

        if (!shortestPath.found()) {
            return Optional.empty();
        }
        List<Integer> shortestValue = shortestPath.value;
        Integer node1 = shortestValue.get(shortestValue.size() - 2);
        Integer node2 = shortestValue.get(shortestValue.size() - 1);
        return Optional.of(new Link(node1, node2));
    }

    private void shortestPathToGateway(ShortestPath shortestPath, BobNet bobNet, List<Integer> currentPath) {
        if (shortestPath.found() && shortest(bobNet, currentPath, shortestPath.value) != currentPath) {
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

    private List<Integer> shortest(BobNet bobNet, List<Integer> path1, List<Integer> path2) {
        if (path1.size() < path2.size()) {
            return path1;
        }
        if (path2.size() < path1.size()) {
            return path2;
        }
        Integer node1 = path1.get(path1.size() - 2);
        int nbGateway1 = bobNet.countGatewaysLinkedTo(node1);
        Integer node2 = path1.get(path1.size() - 2);
        int nbGateway2 = bobNet.countGatewaysLinkedTo(node2);
        return nbGateway1 >= nbGateway2 ? path1 : path2;
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
