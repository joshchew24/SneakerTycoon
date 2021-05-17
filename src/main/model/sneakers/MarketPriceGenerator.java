package model.sneakers;

/*
 * Tool for generating randomized floats representing the market price of a sneaker
 */
// TODO: add a cooldown for updating market prices?
public class MarketPriceGenerator {

    // market price ranges for different rarities
    public static final float COMMON_MIN_DELTA = 10;
    public static final float COMMON_MAX_DELTA = 30;
    public static final float UNCOMMON_MIN_DELTA = 10;
    public static final float UNCOMMON_MAX_DELTA = 70;
    public static final float RARE_MIN_DELTA = -50;
    public static final float RARE_MAX_DELTA = 300;
    public static final float GRAIL_MIN_DELTA = -1900;
    public static final float GRAIL_MAX_DELTA = 3000;
    public static final float LEGENDARY_MIN_DELTA = -3700;
    public static final float LEGENDARY_MAX_DELTA = 20000;

    private float min;
    private float max;

    // EFFECTS: constructs a tool to generate market prices for sneakers
    public MarketPriceGenerator() {
    }

    // EFFECTS: generates a market price for a given sneaker type based on rarity and retail price
    public float generatePrice(SneakerType type) {
        switch (type.getRarity()) {
            case COMMON:
                setMinMax(type.getRetailPrice(), COMMON_MIN_DELTA, COMMON_MAX_DELTA);
                return randomizePrice();
            case UNCOMMON:
                setMinMax(type.getRetailPrice(), UNCOMMON_MIN_DELTA, UNCOMMON_MAX_DELTA);
                return randomizePrice();
            case RARE:
                setMinMax(type.getRetailPrice(), RARE_MIN_DELTA, RARE_MAX_DELTA);
                return randomizePrice();
            case GRAIL:
                setMinMax(type.getRetailPrice(), GRAIL_MIN_DELTA, GRAIL_MAX_DELTA);
                return randomizePrice();
            default:
                setMinMax(type.getRetailPrice(), LEGENDARY_MIN_DELTA, LEGENDARY_MAX_DELTA);
                return randomizePrice();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets min and max range of price generator using the base retail price, and the desired range
    private void setMinMax(float retail, float minDelta, float maxDelta) {
        min = retail - minDelta;
        max = retail + maxDelta;
    }

    // MODIFIES: this
    // EFFECTS: generates a random price within current range
    private float randomizePrice() {
        return (float) (Math.random() * (max - min + 1) + min);
    }
}