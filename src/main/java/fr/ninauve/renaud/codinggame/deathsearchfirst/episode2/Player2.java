package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Player2 {

    public Optional<Link> nextLinkToCut(Network network, Node agentNode) {
        Optional<Link> directGatewayLink = directGatewayLink(agentNode);
        if (directGatewayLink.isPresent()) {
            return directGatewayLink;
        }
        return nearestGatewayLink(network, agentNode);
    }

    private Optional<Link> directGatewayLink(Node agentNode) {
        List<Node> directGateways = agentNode.neighbourGateways().toList();
        if (!directGateways.isEmpty()) {
            return Optional.of(new Link(agentNode, directGateways.get(0)));
        }
        return Optional.empty();
    }

    private Optional<Link> nearestGatewayLink(Network network, Node agentNode) {
        List<Node> gateways = network.findGateways().toList();
        ShortestPathVisitor shortestVisitor = new ShortestPathVisitor(gateways);
        agentNode.visitDepthFirst(shortestVisitor, Comparator.comparing(Node::value, Comparator.naturalOrder()));
        Path shortest = shortestVisitor.getShortest();
        return Optional.of(new Link(shortest.node(-2), shortest.node(-1)));
    }
}
