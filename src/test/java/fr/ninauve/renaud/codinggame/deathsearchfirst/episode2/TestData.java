package fr.ninauve.renaud.codinggame.deathsearchfirst.episode2;

public class TestData {

    public static Network.Builder networkExample() {
        /*
                0 -   1 -  2 -  (3)
                |             \  |
                |                4
                |                |
                5               (6) -  7 -  (8)
                |  \          /  |  \  |
               (9) - 10 -  11  - 12 -  13
         */
        return Network.network()
                .link(0, 1)
                .link(1, 2)
                .link(2, 3)
                .link(2, 4)
                .link(3, 4)
                .link(4, 6)
                .link(6, 7)
                .link(6, 11)
                .link(6, 12)
                .link(6, 13)
                .link(7, 13)
                .link(7, 8)

                .link(0, 5)
                .link(5, 9)
                .link(5, 10)
                .link(10, 11)
                .link(11, 12)
                .link(12, 13)

                .gateway(3)
                .gateway(6)
                .gateway(8)
                .gateway(9);
    }
}
