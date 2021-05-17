package model;

import model.sneakers.Sneaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Business.getMaxCollectionSize;
import static model.sneakers.MarketPriceGenerator.*;
import static model.sneakers.SneakerType.*;
import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {

    Business myBusiness;
    Sneaker cs1 = new Sneaker(AIR_FORCE_1);
    Sneaker cs2 = new Sneaker(SUPERSTAR);
    Sneaker cs3 = new Sneaker(STAN_SMITH);
    Sneaker us1 = new Sneaker(AIR_MAX_1);
    Sneaker us2 = new Sneaker(ULTRABOOST_1);
    Sneaker us3 = new Sneaker(AIR_MAX_90);
    Sneaker us4 = new Sneaker(AIR_MAX_97);
    Sneaker rs1 = new Sneaker(AIR_JORDAN_1);
    Sneaker rs2 = new Sneaker(YEEZY_350);
    Sneaker gs1 = new Sneaker(SW_97_1);
    Sneaker ls1 = new Sneaker(RED_OCTOBER_YEEZY);
    Sneaker ls2 = new Sneaker(MAG_BTTF);
    List<Sneaker> buyingList;

    // initializes a business, some sneakers, and list of a sneakers for testing
    // sets marketPrice of each sneaker to a known value to facilitate testing
    @BeforeEach
    public void setup() {
        myBusiness = new Business("Test E Co.");
        myBusiness.setBalance(10000);
        cs1.setMarketPrice(120);
        cs2.setMarketPrice(110);
        cs3.setMarketPrice(120);
        us1.setMarketPrice(220);
        us2.setMarketPrice(300);
        us3.setMarketPrice(220);
        us4.setMarketPrice(280);
        rs1.setMarketPrice(400);
        rs2.setMarketPrice(400);
        gs1.setMarketPrice(2200);
        ls1.setMarketPrice(4000);
        ls2.setMarketPrice(5000);
        buyingList = new ArrayList<>();
        buyingList.add(cs1);
        buyingList.add(cs2);
        buyingList.add(us1);
        buyingList.add(us2);
        buyingList.add(rs1);
    }

    // helper for this business to buy a few sneakers
    private void helpBuyFew(List<Sneaker> sneakers) {
        for (Sneaker s : sneakers) {
            myBusiness.buy(s);
        }
    }


    // tests that constructor correctly sets name, balance and collection
    @Test
    public void testConstructor() {
        myBusiness.setBalance(0);
        assertEquals("Test E Co.", myBusiness.getBusinessName());
        assertEquals(0, myBusiness.getBalance());
        assertEquals(0, myBusiness.getCollection().size());
    }


    // tests case where you can't afford to buy something
    @Test
    public void testBuyNotEnough() {
        myBusiness.setBalance(0);
        assertEquals(0, myBusiness.getBalance());
        assertFalse(myBusiness.buy(cs1));
        assertEquals(0, myBusiness.getBalance());
    }

    // tests case where you successfully buy a few sneakers
    @Test
    public void testBuyNormal() {
        assertEquals(10000, myBusiness.getBalance());
        assertTrue(myBusiness.buy(cs1));
        assertEquals(9880, myBusiness.getBalance());
        assertEquals(1, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(cs2));
        assertEquals(9770, myBusiness.getBalance());
        assertEquals(2, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(us1));
        assertEquals(9550, myBusiness.getBalance());
        assertEquals(3, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(us2));
        assertEquals(9250, myBusiness.getBalance());
        assertEquals(4, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(rs1));
        assertEquals(8850, myBusiness.getBalance());
        assertEquals(5, myBusiness.getCollection().size());
    }

    // tests case where you try to buy more sneakers than allowed in your collection
    @Test
    public void testBuyTooMany() {
        helpBuyFew(buyingList);
        assertEquals(5, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(rs2));
        assertEquals(8450, myBusiness.getBalance());
        assertEquals(6, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(gs1));
        assertEquals(6250, myBusiness.getBalance());
        assertEquals(7, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(ls1));
        assertEquals(2250, myBusiness.getBalance());
        assertEquals(8, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(cs3));
        assertEquals(2130, myBusiness.getBalance());
        assertEquals(9, myBusiness.getCollection().size());
        assertFalse(myBusiness.buy(ls2));
        assertEquals(2130, myBusiness.getBalance());
        assertEquals(9, myBusiness.getCollection().size());
        assertTrue(myBusiness.buy(us3));
        assertEquals(1910, myBusiness.getBalance());
        assertEquals(getMaxCollectionSize(), myBusiness.getCollection().size());
        assertFalse(myBusiness.buy(us4));
        assertEquals(1910, myBusiness.getBalance());
        assertEquals(10, myBusiness.getCollection().size());
    }


    // tests case where you try to sell a sneaker when your collection is empty
    @Test
    public void testSellEmptyCollection() {
        assertEquals(0, myBusiness.getCollection().size());
        assertNull(myBusiness.sell(3));
        assertEquals(0, myBusiness.getCollection().size());
    }

    // tests case where you sell one sneaker
    @Test
    public void testSellOne() {
        helpBuyFew(buyingList);
        assertEquals(5, myBusiness.getCollection().size());
        assertEquals(cs1, myBusiness.sell(1));
        assertEquals(4, myBusiness.getCollection().size());
    }

    // tests case where you sell your whole collection
    @Test
    public void testSellAll() {
        helpBuyFew(buyingList);
        assertEquals(5, myBusiness.getCollection().size());
        assertEquals(cs1, myBusiness.sell(1));
        assertEquals(4, myBusiness.getCollection().size());
        assertEquals(cs2, myBusiness.sell(1));
        assertEquals(3, myBusiness.getCollection().size());
        assertEquals(us1, myBusiness.sell(1));
        assertEquals(2, myBusiness.getCollection().size());
        assertEquals(us2, myBusiness.sell(1));
        assertEquals(1, myBusiness.getCollection().size());
        assertEquals(rs1, myBusiness.sell(1));
        assertEquals(0, myBusiness.getCollection().size());
    }


    // tests randomizing prices of an empty collection
    @Test
    public void testRandomizeCollectionPricesNone() {
        assertEquals(0, myBusiness.getCollection().size());
        myBusiness.randomizeCollectionMarketPrices();
        assertEquals(0, myBusiness.getCollection().size());
    }

    // tests randomizing your collection's market prices, ensuring each sneaker's market price ends up
    // within an appropriate range
    @Test
    public void testRandomizeCollectionPricesMany() {
        helpBuyFew(buyingList);
        assertEquals(120, myBusiness.getCollection().get(0).getMarketPrice());
        assertEquals(110, myBusiness.getCollection().get(1).getMarketPrice());
        assertEquals(220, myBusiness.getCollection().get(2).getMarketPrice());
        assertEquals(300, myBusiness.getCollection().get(3).getMarketPrice());
        assertEquals(400, myBusiness.getCollection().get(4).getMarketPrice());
        myBusiness.randomizeCollectionMarketPrices();
        assertTrue(checkMarketPriceInRange(myBusiness.getCollection().get(0), COMMON_MIN_DELTA, COMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(myBusiness.getCollection().get(1), COMMON_MIN_DELTA, COMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(myBusiness.getCollection().get(2), UNCOMMON_MIN_DELTA, UNCOMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(myBusiness.getCollection().get(3), UNCOMMON_MIN_DELTA, UNCOMMON_MAX_DELTA));
        assertTrue(checkMarketPriceInRange(myBusiness.getCollection().get(4), RARE_MIN_DELTA, RARE_MAX_DELTA));
    }

    // helper for testing price randomization. see MarketPriceGeneratorTest for more
    private boolean checkMarketPriceInRange(Sneaker s, float minDelta, float maxDelta) {
        float min = s.getRetailPrice() - minDelta;
        float max = s.getRetailPrice() + maxDelta;
        float marketPrice = s.getMarketPrice();
        return ((min <= marketPrice) && (marketPrice <= max));
    }


    // tests case where you get information about an empty collection
    @Test
    public void testGetCollectionInfoEmpty() {
        List<String> emptyList = new ArrayList<>();
        assertEquals(emptyList, myBusiness.getCollectionInfo());
    }

    // tests that getCollectionInfo is returning the proper format and information
    @Test
    public void testGetCollectionInfoMany() {
        helpBuyFew(buyingList);
        List<String> collectionInfo = new ArrayList<>();
        collectionInfo.add(myBusiness.getCollection().get(0).getInfo() + "\n");
        collectionInfo.add(myBusiness.getCollection().get(1).getInfo() + "\n");
        collectionInfo.add(myBusiness.getCollection().get(2).getInfo() + "\n");
        collectionInfo.add(myBusiness.getCollection().get(3).getInfo() + "\n");
        collectionInfo.add(myBusiness.getCollection().get(4).getInfo() + "\n");
        assertEquals(collectionInfo, myBusiness.getCollectionInfo());
    }
}