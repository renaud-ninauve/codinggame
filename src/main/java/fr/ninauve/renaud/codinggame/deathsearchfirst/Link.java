package fr.ninauve.renaud.codinggame.deathsearchfirst;

import java.util.Objects;

public class Link {
    public final int node1;
    public final int node2;

    public Link(int node1, int node2) {
        this.node1 = Math.min(node1, node2);
        this.node2 = Math.max(node1, node2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link link)) return false;
        return node1 == link.node1 && node2 == link.node2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2);
    }
}
