package fr.ninauve.renaud.codinggame.deathsearchfirst;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return links.getOrDefault(node, new ArrayList<>());
    }

    public List<Integer> findGatewaysLinkedTo(int node) {
        return findNodesLinkedTo(node).stream()
                .filter(this::isGateway)
                .toList();
    }

    public int countGatewaysLinkedTo(int node) {
        return (int) findNodesLinkedTo(node).stream()
                .filter(this::isGateway)
                .limit(2)
                .count();
    }

    public List<Integer> getNodesLinkedToNNodesOf(List<Integer> nodes, long count) {
        final Set<Integer> distinctNodes = new HashSet<>(nodes);
        final Map<Integer, Long> gatewaysPerNode = distinctNodes.stream()
                .map(gateway -> links.getOrDefault(gateway, List.of()))
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Long, List<Integer>> nodesByCount = gatewaysPerNode.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        return nodesByCount
                .getOrDefault(count, List.of());
    }

    public List<Integer> getGatewaysLinkedTo(List<Integer> nodes) {
        return nodes.stream()
                .map(this::findNodesLinkedTo)
                .flatMap(List::stream)
                .filter(this::isGateway)
                .toList();
    }

    public boolean isGateway(int node) {
        return gateways.contains(node);
    }

    public List<Integer> getGateways() {
        return gateways;
    }

    public void removeLink(Link link) {
        links.get(link.node1).remove(Integer.valueOf(link.node2));
        links.get(link.node2).remove(Integer.valueOf(link.node1));
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
