package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.HashMap;
import java.util.Map;

import static fr.ninauve.renaud.codinggame.deathsearchfirst.episode2.Node.node;

public class Network {
    private final Map<Integer, Node> nodes = new HashMap<>();

    public static Builder network() {
        return new Builder();
    }

    public static class Builder {

        private final Network network = new Network();
        public Builder link(int a, int b) {
            Node nodeA = network.createNode(a);
            Node nodeB = network.createNode(b);
            network.addLink(nodeA, nodeB);
            return this;
        }

        public Builder gateway(int gateway) {
            Node gatewayNode = network.createNode(gateway);
            gatewayNode.setAsGateway();
            return this;
        }
        public Network build() {
            return network;
        }

    }
    public void addLink(Node a, Node b) {
        a.addNeighbour(b);
        b.addNeighbour(a);
    }

    public void deleteLink(Node a, Node b) {
        a.removeNeighbour(b);
        b.removeNeighbour(a);
    }

    public Node createNode(int value) {
        nodes.putIfAbsent(value, node(value));
        return getNode(value);
    }

    public Node getNode(int value) {
        return nodes.get(value);
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}