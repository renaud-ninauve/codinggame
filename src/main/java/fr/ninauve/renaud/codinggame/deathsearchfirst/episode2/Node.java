package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.*;
import java.util.stream.Stream;

public class Node {
    private final int value;
    private Type type = Type.NORMAL;
    private final Set<Node> neighbours = new HashSet<>();

    public static Node node(int value, Node... neighbours) {
        Node node = new Node(value);
        node.addNeighbours(Arrays.asList(neighbours));
        return node;
    }

    public interface Visitor {
        boolean start(Node node);
        void end(Node node);
    }

    private Node(int value) {
        this.value = value;
    }

    public void addNeighbour(Node neighbour) {
        this.neighbours.add(neighbour);
    }

    public void addNeighbours(List<Node> neighbours) {
        this.neighbours.addAll(neighbours);
    }

    public void removeNeighbour(Node neighbour) {
        this.neighbours.remove(neighbour);
    }

    public boolean isGateway() {
        return type == Type.GATEWAY;
    }

    public void setAsGateway() {
        this.type = Type.GATEWAY;
    }

    public Stream<Node> neighbours() {
        return neighbours.stream();
    }

    public Stream<Node> neighbourGateways() {
        return neighbours()
                .filter(Node::isGateway);
    }

    public void visitDepthFirst(Visitor visitor, Comparator<Node> visitOrder) {
        boolean shouldContinue = visitor.start(this);
        if (shouldContinue) {
            neighbours()
                    .sorted(visitOrder)
                    .forEach(neighbour -> neighbour.visitDepthFirst(visitor, visitOrder));
            visitor.end(this);
        }
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    private enum Type {
        NORMAL, GATEWAY;
    }
}