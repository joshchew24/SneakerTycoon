package persistence;

import model.sneakers.Sneaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    // EFFECTS: helper for testing if a given Sneaker has the expectedPurchasePrice and expectedName
    protected void checkSneaker(float expectedPurchasePrice, String expectedName, Sneaker s) {
        assertEquals(expectedPurchasePrice, s.getPurchasedPrice());
        assertEquals(expectedName, s.getName());
    }
}
