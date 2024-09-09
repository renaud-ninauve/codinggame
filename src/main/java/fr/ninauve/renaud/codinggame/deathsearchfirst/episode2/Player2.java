package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import fr.ninauve.renaud.codinggame.deathsearchfirst.BobNet;
import fr.ninauve.renaud.codinggame.deathsearchfirst.Link;

import java.util.*;
import java.util.function.Function;

public class Player2 {
    public Optional<Link> nextLinkToSever(BobNet bobnet, int virusPosition) {
        ShortestPaths shortestPaths = new ShortestPaths();
        shortestPathToGateway(shortestPaths, bobnet, List.of(virusPosition));

        if (!shortestPaths.found()) {
            return Optional.empty();
        }
        List<Integer> shortestValue = shortestPaths.value;
        Integer node1 = shortestValue.get(shortestValue.size() - 2);
        Integer node2 = shortestValue.get(shortestValue.size() - 1);
        return Optional.of(new Link(node1, node2));
    }

    private static void shortestPathToGateway(ShortestPaths shortestPaths, BobNet bobNet, List<Integer> currentPath) {
        if (shortestPaths.found() && compare(bobNet, currentPath, shortestPaths.value) > 1) {
            return;
        }
        final Integer currentPosition = currentPath.get(currentPath.size() - 1);
        if (bobNet.isGateway(currentPosition)) {
            shortestPaths.value = shortestPaths.found()
                ? shortest(bobNet, currentPath, shortestPaths.value)
                : currentPath;
            return;
        }

        final List<Integer> targets = bobNet.findNodesLinkedTo(currentPosition);
        targets.sort((a, b) -> Boolean.compare(shortestPaths.pastValues.contains(b), shortestPaths.pastValues.contains(a)));
        for (int target : targets) {
            if (currentPath.contains(target)) {
                continue;
            }
            final List<Integer> newPath = new ArrayList<>();
            newPath.addAll(currentPath);
            newPath.add(target);
            shortestPathToGateway(shortestPaths, bobNet, newPath);
        }
    }

    private static List<Integer> shortest(BobNet bobNet, List<Integer> path1, List<Integer> path2) {
        int result = compare(bobNet, path1, path2);
        return result <= 0 ? path1 : path2;
    }

    private static int compare(BobNet bobNet, List<Integer> path1, List<Integer> path2) {
        if (path1.size() < path2.size()) {
            return -1;
        }
        if (path2.size() < path1.size()) {
            return 1;
        }
        Integer node1 = path1.get(path1.size() - 2);
        int nbGateway1 = bobNet.countGatewaysLinkedTo(node1);
        Integer node2 = path2.get(path1.size() - 2);
        int nbGateway2 = bobNet.countGatewaysLinkedTo(node2);
        if (nbGateway1 > nbGateway2) {
            return -1;
        }
        if (nbGateway2 > nbGateway1) {
            return 1;
        }
        return 0;
    }


    private static class ShortestPaths {
        private List<Integer> value;
        private Set<Integer> pastValues = new HashSet<>();

        private boolean found() {
            return value != null;
        }

        private void setValue(List<Integer> value) {
            this.value = value;
            this.pastValues.addAll(value);
        }

        private int size() {
            return value.size();
        }
    }
}
