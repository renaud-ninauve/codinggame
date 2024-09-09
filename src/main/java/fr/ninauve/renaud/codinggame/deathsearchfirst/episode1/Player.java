package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import java.util.List;
import java.util.Optional;

public class Player {
    public Optional<Link> nextLinkToSever(BobNet bobnet, int virusPosition) {
        final List<Integer> virusTargets = bobnet.findNodesLinkedTo(virusPosition);
        return virusTargets.stream()
                .filter(bobnet::isGateway)
                .map(gateway -> new Link(virusPosition, gateway))
                .findFirst();
    }
}
