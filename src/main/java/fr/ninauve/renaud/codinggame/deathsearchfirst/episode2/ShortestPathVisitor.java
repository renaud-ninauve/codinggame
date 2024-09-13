package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.List;

public class ShortestPathVisitor implements Node.Visitor {
    private final List<Node> destinations;
    private Path shortest;
    private Path currentPath;

    public ShortestPathVisitor(List<Node> destinations) {
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

    public Path getShortest() {
        return shortest;
    }
}
