package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Path implements Comparable<Path> {
    private final List<Node> value;

    public Path(List<Node> value) {
        this.value = value;
    }

    public Path withNewNode(Node newNode) {
        final List<Node> newPath = new ArrayList<>(value);
        newPath.add(newNode);
        return new Path(newPath);
    }

    public Path withoutLast() {
        if (value.isEmpty()) {
            return this;
        }
        final List<Node> newPath = new ArrayList<>(value.subList(0, value.size()-1));
        return new Path(newPath);
    }

    public int distance() {
        return value.size();
    }

    public Node node(int index) {
        return index >= 0 ? value.get(index) : value.get(value.size() + index);
    }

    public boolean contains(Node node) {
        return value.contains(node);
    }

    public Stream<Node> stream() {
        return value.stream();
    }

    @Override
    public int compareTo(Path other) {
        return Comparator.comparing(Path::weight)
                .compare(this, other);
    }

    private int weight() {
        return value.stream()
                .mapToInt(node -> node.neighbourGateways().count() > 0 ? 0 : 1)
                .sum();
    }

    @Override
    public String toString() {
        return stream().map(Node::value).map(Object::toString).collect(Collectors.joining(","));
    }
}