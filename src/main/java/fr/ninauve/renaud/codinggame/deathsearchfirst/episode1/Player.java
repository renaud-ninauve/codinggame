package fr.ninauve.renaud.codinggame.deathsearchfirst.episode1;

import java.util.List;

public class Player {
    public Link nextLinkToSever(BobNet bobnet, int virusPosition) {
        final List<Integer> virusTargets = bobnet.findNodesLinkedTo(virusPosition);
        return new Link(virusPosition, virusTargets.get(0));
    }
}
