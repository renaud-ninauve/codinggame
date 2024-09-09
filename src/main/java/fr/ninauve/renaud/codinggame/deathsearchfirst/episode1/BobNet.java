package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BobNet {
    private final Map<Integer, List<Integer>> links;
    private final List<Integer> gateways;

    public BobNet(Map<Integer, List<Integer>> links, List<Integer> gateways) {
        this.links = links;
        this.gateways = gateways;
    }

    public static BobNetBuilder with() {
        return new BobNetBuilder();
    }

    public List<Integer> findNodesLinkedTo(int node) {
        return links.getOrDefault(node, List.of());
    }

    public boolean isGateway(int node) {
        return gateways.contains(node);
    }

    public static class BobNetBuilder {
        private final Map<Integer, List<Integer>> links = new HashMap<>();
        private final List<Integer> gateways = new ArrayList<>();

        public BobNetBuilder linkBetween(int node1, int node2) {
            putLink(node1, node2);
            putLink(node2, node1);
            return this;
        }

        public BobNetBuilder gatewayAt(int node) {
            gateways.add(node);
            return this;
        }

        private void putLink(int node1, int node2) {
            if (!links.containsKey(node1)) {
                links.put(node1, new ArrayList<>());
            }
            links.get(node1).add(node2);
        }

        public BobNet build() {
            return new BobNet(links, gateways);
        }
    }
}
