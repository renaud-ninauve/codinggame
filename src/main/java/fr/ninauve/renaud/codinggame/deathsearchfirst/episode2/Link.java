package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

import java.util.Objects;

public class Link {
    private final Node a;
    private final Node b;

    public Link(Node a, Node b) {
        this.a = a;
        this.b = b;
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link link)) return false;
        return (Objects.equals(a, link.a) || Objects.equals(a, link.b))
                && (Objects.equals(b, link.a) || Objects.equals(b, link.b));
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Link{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
