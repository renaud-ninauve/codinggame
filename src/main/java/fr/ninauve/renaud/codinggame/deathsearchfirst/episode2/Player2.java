package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import fr.ninauve.renaud.codinggame.deathsearchfirst.BobNet;
import fr.ninauve.renaud.codinggame.deathsearchfirst.Link;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player2 {
    public Optional<Link> nextLinkToSever(BobNet bobnet, int virusPosition) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(bobnet);
        shortestPathFinder.find(Path.path(virusPosition));
        ShortestPaths shortestPaths = shortestPathFinder.shortestPaths;
        if (shortestPaths.isEmpty() || shortestPaths.shortestSize() < 2) {
            return Optional.empty();
        }
        if (shortestPaths.shortestSize() == 2) {
            Path shortest = shortestPaths.shortests.get(0);
            return Optional.of(new Link(shortest.node(-2), shortest.node(-1)));
        }
        final List<Integer> lastNodes = shortestPaths.stream()
                .map(path -> path.node(-2))
                .toList();
        final List<Integer> nearestGateways = shortestPaths.stream()
                .map(path -> path.node(-1))
                .toList();
        final List<Integer> nearestDoubleNodes = bobnet.getNodesLinkedToNNodesOf(nearestGateways, 2);
        if (!nearestDoubleNodes.isEmpty()) {
            Integer nearestDoubleNode = nearestDoubleNodes.get(0);
            Integer nearestGateway = bobnet.findGatewaysLinkedTo(nearestDoubleNode).stream()
                    .filter(nearestGateways::contains)
                    .findAny()
                    .orElseThrow();
            return Optional.of(new Link(nearestDoubleNode, nearestGateway));
        }
        final List<Integer> allDoubleNodes = bobnet.getNodesLinkedToNNodesOf(bobnet.getGateways(), 2);
        Optional<Integer> doubleGateway = nearestGateways.stream()
                .map(gateway -> Map.entry(gateway, bobnet.findNodesLinkedTo(gateway).stream().anyMatch(allDoubleNodes::contains)))
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .findFirst();
        if (doubleGateway.isPresent()) {
            return shortestPaths.shortests.stream()
                    .filter(path -> path.node(-1) == doubleGateway.get())
                    .map(shortest -> new Link(shortest.node(-2), shortest.node(-1)))
                    .findFirst();
        }
        return Optional.of(new Link(shortestPaths.shortests.get(0).node(-2), shortestPaths.shortests.get(0).node(-1)));
    }

    private static class ShortestPathFinder {
        private final BobNet bobNet;
        private final ShortestPaths shortestPaths = new ShortestPaths();

        private ShortestPathFinder(BobNet bobNet) {
            this.bobNet = bobNet;
        }

        private void find(Path currentPath) {
            if (shortestPaths.isShorterThan(currentPath)) {
                return;
            }
            final int currentNode = currentPath.node(-1);
            if (bobNet.isGateway(currentNode)) {
                shortestPaths.pathFound(currentPath);
                return;
            }

            final List<Integer> targets = bobNet.findNodesLinkedTo(currentNode);
            targets.sort(Comparator.comparing(shortestPaths::distanceOf));
            for (int target : targets) {
                if (currentPath.contains(target)) {
                    continue;
                }
                find(currentPath.withNewNode(target));
            }
        }
    }

    private static class ShortestPaths {
        private final List<Path> shortests = new ArrayList<>();
        private final Map<Integer, Integer> distances = new HashMap<>();

        private void pathFound(Path path) {
            updateShortestDistancesFrom(path);
            if (isEmpty()) {
                shortests.add(path);
                return;
            }
            if (path.size() == shortestSize()) {
                shortests.add(path);
                return;
            }
            if (path.size() < shortestSize()) {
                shortests.clear();
                shortests.add(path);
            }
        }

        private void updateShortestDistancesFrom(Path path) {
            for (int distance = 0; distance < path.size(); distance++) {
                int node = path.node(distance);
                distances.putIfAbsent(node, Integer.MAX_VALUE);
                distances.put(node, Math.min(distance, distanceOf(node)));
            }
        }

        private int distanceOf(int node) {
            return distances.getOrDefault(node, Integer.MAX_VALUE);
        }

        private boolean isEmpty() {
            return shortests.isEmpty();
        }

        private boolean isShorterThan(Path path) {
            return !shortests.isEmpty()
                    && shortests.get(0).size() < path.size();
        }

        private Stream<Path> stream() {
            return shortests.stream();
        }

        private int shortestSize() {
            return isEmpty() ? Integer.MAX_VALUE : shortests.get(0).size();
        }
    }

    private static class Path implements Comparable<Path> {
        private final List<Integer> value;

        private static Path path(int startingNode) {
            return new Path(List.of(startingNode));
        }

        private Path(List<Integer> value) {
            this.value = value;
        }

        private Path withNewNode(int newNode) {
            final List<Integer> newPath = new ArrayList<>(value);
            newPath.add(newNode);
            return new Path(newPath);
        }

        private int size() {
            return value.size();
        }

        private int node(int index) {
            return index >= 0 ? value.get(index) : value.get(value.size() + index);
        }

        private boolean contains(int node) {
            return value.contains(node);
        }

        private Stream<Integer> stream() {
            return value.stream();
        }

        @Override
        public int compareTo(Path other) {
            return Comparator.comparing(Path::size)
                    .compare(this, other);
        }
    }
}
