package model.sneakers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static model.sneakers.MarketPriceGenerator.*;
import static model.sneakers.Rarity.*;

public class MarketPriceGeneratorTest {

    private MarketPriceGenerator mpg;

    // initializes a MarketPriceGenerator
    @BeforeEach
    public void setup() {
        mpg = new MarketPriceGenerator();
    }

    // checks that marketPriceGenerator is returning prices within the desired range based on rarity
    @Test
    public void testGeneratePrice() {
        assertTrue(testGeneratePriceHelper(COMMON, COMMON_MIN_DELTA, COMMON_MAX_DELTA));
        assertTrue(testGeneratePriceHelper(UNCOMMON, UNCOMMON_MIN_DELTA, UNCOMMON_MAX_DELTA));
        assertTrue(testGeneratePriceHelper(RARE, RARE_MIN_DELTA, RARE_MAX_DELTA));
        assertTrue(testGeneratePriceHelper(GRAIL, GRAIL_MIN_DELTA, GRAIL_MAX_DELTA));
        assertTrue(testGeneratePriceHelper(LEGENDARY, LEGENDARY_MIN_DELTA, LEGENDARY_MAX_DELTA));
    }

    // helper for checking that a randomized market price is within range
    //  range is a sneaker's retail price +/- maxDelta/minDelta
    private boolean testGeneratePriceHelper(Rarity rarity, float minDelta, float maxDelta) {
        Sneaker s = new Sneaker(rarity);
        float min = s.getRetailPrice() - minDelta;
        float max = s.getRetailPrice() + maxDelta;
        float marketPrice = mpg.generatePrice(s.getType());
        return ((min <= marketPrice) && (marketPrice <= max));
    }

}
