package model.sneakers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import model.Business;

import static model.sneakers.MarketPriceGenerator.*;
import static model.sneakers.Rarity.*;
import static model.sneakers.SneakerType.getAllOfRarity;
import static model.sneakers.SneakerType.SW_97_1;
import static org.junit.jupiter.api.Assertions.*;

public class SneakerTest {

    private Sneaker cs;
    private Sneaker us;
    private Sneaker rs;
    private Sneaker gs;
    private Sneaker ls;
    private Sneaker testSneaker;
    List<Sneaker> testSneakers;

    // initializes some sneakers for testing
    @BeforeEach
    public void setup() {
        cs = new Sneaker(COMMON);
        us = new Sneaker(UNCOMMON);
        rs = new Sneaker(RARE);
        gs = new Sneaker(GRAIL);
        ls = new Sneaker(LEGENDARY);

        testSneakers = Arrays.asList(cs, us, rs, gs, ls);

        testSneaker = new Sneaker(SW_97_1);
    }

    // tests constructor is correctly assigning fields
    @Test
    public void testConstructor() {
        for (Sneaker s : testSneakers) {
            assertTrue(checkNameValid(s));
            assertTrue(checkRetailPrice(s));
            assertFalse(s.getIsPurchased());
        }
    }

    // helper for checking if a name is valid
    private boolean checkNameValid(Sneaker s) {
        String name = s.getName();
        EnumSet<SneakerType> allOfRarity = getAllOfRarity(s.getRarity());
        ArrayList<String> allOfRarityLabels = new ArrayList<>();
        for (SneakerType st : allOfRarity) {
            allOfRarityLabels.add(st.getLabel());
        }
        return allOfRarityLabels.contains(name);
    }

    private boolean checkRetailPrice(Sneaker s) {
        float retailPrice = s.getRetailPrice();
        EnumSet<SneakerType> allOfRarity = getAllOfRarity(s.getRarity());
        ArrayList<Float> allOfRarityRPs = new ArrayList<>();
        for (SneakerType st : allOfRarity) {
            allOfRarityRPs.add(st.getRetailPrice());
        }
        return allOfRarityRPs.contains(retailPrice);
    }

    // tests constructor is correctly assigning rarities
    @Test
    public void testConstructorRarity() {
        assertEquals(COMMON, cs.getRarity());
        assertEquals(UNCOMMON, us.getRarity());
        assertEquals(RARE, rs.getRarity());
        assertEquals(GRAIL, gs.getRarity());
        assertEquals(LEGENDARY, ls.getRarity());
    }


    // tests that getInfo is returning correct string format and info
    @Test
    public void testGetInfoNotYetPurchased() {
        assertEquals("Name: Sean Wotherspoon Air Max 1/97\n"
                        + "Rarity: GRAIL\n"
                        + "Retail Price: $210.0\n"
                        + "Market Price: $" + testSneaker.getMarketPrice() + "\n",
                testSneaker.getInfo());
    }

    // tests getInfo when sneaker is purchased
    @Test
    public void testGetInfoPurchased() {
        Business testBusiness = new Business("test");
        testBusiness.setBalance(10000);
        testBusiness.buy(testSneaker);
        assertEquals("Name: Sean Wotherspoon Air Max 1/97\n"
                        + "Rarity: GRAIL\n"
                        + "Retail Price: $210.0\n"
                        + "Market Price: $" + testSneaker.getMarketPrice() + "\n"
                        + "Purchased for: $" + testSneaker.getPurchasedPrice() + "\n",
                testSneaker.getInfo());
    }


    // tests that randomizeMarketPrice is setting a sneaker's price within the appropriate range.
    @Test
    public void testGetRandomizedPrice() {
//        MarketPriceGenerator mpg = new MarketPriceGenerator();
        for (Sneaker s : testSneakers) {
            s.randomizeMarketPrice();
        }
        assertTrue(checkMarketPriceInRange(cs, COMMON_MIN_DELTA, COMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(us, UNCOMMON_MIN_DELTA, UNCOMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(rs, RARE_MIN_DELTA, RARE_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(gs, GRAIL_MIN_DELTA, GRAIL_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(ls, LEGENDARY_MIN_DELTA, LEGENDARY_MAX_DELTA));
    }

    // helper for testing price randomization. see MarketPriceGeneratorTest for more
    private boolean checkMarketPriceInRange(Sneaker s, float minDelta, float maxDelta) {
        float min = s.getRetailPrice() - minDelta;
        float max = s.getRetailPrice() + maxDelta;
        float marketPrice = s.getMarketPrice();
        return ((min <= marketPrice) && (marketPrice <= max));
    }

    @Test
    public void testToString() {
        assertEquals("Sean Wotherspoon Air Max 1/97", testSneaker.toString());
    }

    @Test
    public void testGetImagePath() {
        assertEquals("./data/assets/sw97-1.png", testSneaker.getImagePath());
    }
}
