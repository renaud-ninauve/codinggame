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
        Optional<Link> nearestDoubleGatewayLink = nearestDoubleGatewayLink(network, agentNode);
        if (nearestDoubleGatewayLink.isPresent()) {
            return nearestDoubleGatewayLink;
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

    private Optional<Link> nearestDoubleGatewayLink(Network network, Node agentNode) {
        List<Node> nodesLinkedToSeveralGateways = network.findNodesLinkedToSeveralGateways();
        if (nodesLinkedToSeveralGateways.isEmpty()) {
            return Optional.empty();
        }
        ShortestPathVisitor shortestVisitor = new ShortestPathVisitor(nodesLinkedToSeveralGateways);
        agentNode.visitDepthFirst(shortestVisitor, Comparator.comparing(Node::value, Comparator.naturalOrder()));
        Path shortest = shortestVisitor.getShortest();
        if (shortest == null) {
            return Optional.empty();
        }
        Node doubleNode = shortest.node(-1);
        return Optional.of(new Link(doubleNode, doubleNode.neighbourGateways().findFirst().orElseThrow()));
    }

    private Optional<Link> nearestGatewayLink(Network network, Node agentNode) {
        List<Node> gateways = network.findGateways();
        ShortestPathVisitor shortestVisitor = new ShortestPathVisitor(gateways);
        agentNode.visitDepthFirst(shortestVisitor, Comparator.comparing(Node::value, Comparator.naturalOrder()));
        Path shortest = shortestVisitor.getShortest();
        if (shortest == null) {
            return Optional.empty();
        }
        return Optional.of(new Link(shortest.node(-2), shortest.node(-1)));
    }
}
