package model;

import model.sneakers.Sneaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.sneakers.SneakerType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketplaceTest {

    Marketplace testMP;
    Sneaker us1 = new Sneaker(NMD_R1);
    Sneaker rs1 = new Sneaker(AIR_JORDAN_3);
    Sneaker rs2 = new Sneaker(AIR_JORDAN_4);
    Sneaker rs3 = new Sneaker(AIR_JORDAN_11);
    Sneaker gs1 = new Sneaker(CHICAGO_AIR_JORDAN_1);

    // initializes a marketplace to test with
    @BeforeEach
    public void setup() {
        testMP = new Marketplace();
    }

    /*
     * Tests that the constructor is correctly randomizing the listings in the marketplace.
     *
     * Since LEGENDARY sneakers only have a chance of 0.01% to generate, this test will iterate 20000 times to
     * try to show that all sneaker types can eventually show up in the marketplace.
     */
    @Test
    public void testConstructor() {
        for (int i = 0; i <= 20000; i++) {
            testMP = new Marketplace();
            List<Sneaker> currentListings = testMP.getListings();
            assertEquals(5, currentListings.size());
            for (Sneaker s : currentListings) {
                assertTrue(getAllSneakerTypes().contains(s.getType()));
            }
        }
    }


    // tests getting listingInfo when empty
    @Test
    public void testGetListingsInfoEmpty() {
        // first empty the listings
        testMP.getListings().subList(0, 5).clear();
        assertEquals(0, testMP.getListingsInfo().size());
    }

    // tests that getListingsInfo is returning a list with proper string formatting and information
    @Test
    public void testGetListingsInfoFull() {
        // first empty the listings so we can add custom sneakers with known values
        List<String> comparisonList = new ArrayList<>();
        comparisonList.add(us1.getInfo() + "\n");
        comparisonList.add(rs1.getInfo() + "\n");
        comparisonList.add(rs2.getInfo() + "\n");
        comparisonList.add(rs3.getInfo() + "\n");
        comparisonList.add(gs1.getInfo() + "\n");
        testMP.getListings().subList(0, 5).clear();
        testMP.getListings().add(us1);
        testMP.getListings().add(rs1);
        testMP.getListings().add(rs2);
        testMP.getListings().add(rs3);
        testMP.getListings().add(gs1);

        assertEquals(comparisonList, testMP.getListingsInfo());
    }
}
