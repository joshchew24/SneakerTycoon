package model.sneakers;

import org.json.JSONObject;
import persistence.Writable;

import java.util.EnumSet;
import java.util.Random;

/*
 * Represents a sneaker.
 */

public class Sneaker implements Writable {

    private final String name;
    private final Rarity rarity;
    private final float retailPrice;
    private SneakerType type;

    private float purchasedPrice;
    private float marketPrice;
    private boolean isPurchased;

    MarketPriceGenerator marketPriceGenerator = new MarketPriceGenerator();

    // EFFECTS: constructs a random sneaker of given rarity, with random market price
    public Sneaker(Rarity rarity) {
        selectOfRarity(rarity);

        name = type.getLabel();
        this.rarity = rarity;
        retailPrice = type.getRetailPrice();
        marketPrice = marketPriceGenerator.generatePrice(type);
        isPurchased = false;
    }

    // note: currently only used in testing and for persistence
    // EFFECTS: constructs a sneaker of given type with randomized market price
    public Sneaker(SneakerType type) {
        this.type = type;
        name = type.getLabel();
        rarity = type.getRarity();
        retailPrice = type.getRetailPrice();
        marketPrice = marketPriceGenerator.generatePrice(type);
    }


    // MODIFIES: this
    // EFFECTS: sets sneaker type to random one of given rarity
    private void selectOfRarity(Rarity rarity) {
        Random rand = new Random();
        EnumSet<SneakerType> raritySet = SneakerType.getAllOfRarity(rarity);
        int upperbound = raritySet.size();
        int choice = rand.nextInt(upperbound);

        type = SneakerType.get(raritySet, choice);
    }

    // MODIFIES: this
    // EFFECTS: sets a randomized marketPrice for the sneaker.
    public void randomizeMarketPrice() {
        marketPrice = marketPriceGenerator.generatePrice(type);
    }

    // TODO: if purchased price is 0, also return price premium over retail
    // EFFECTS: checks if sneaker has been purchased, then returns information about this sneaker in string format.
    //          purchased sneakers also show the price they were bought at.
    public String getInfo() {
        if (purchasedPrice == 0.0) {
            return ("Name: " + name + "\n"
                    + "Rarity: " + rarity + "\n"
                    + "Retail Price: $" + retailPrice + "\n"
                    + "Market Price: $" + marketPrice + "\n");
        } else {
            return ("Name: " + name + "\n"
                    + "Rarity: " + rarity + "\n"
                    + "Retail Price: $" + retailPrice + "\n"
                    + "Market Price: $" + marketPrice + "\n"
                    + "Purchased for: $" + purchasedPrice + "\n");
        }
    }


    // getters:
    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public SneakerType getType() {
        return type;
    }

    public float getPurchasedPrice() {
        return purchasedPrice;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public boolean getIsPurchased() {
        return isPurchased;
    }


    // setters:
    public void setPurchasedPrice(float purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    // EFFECTS: returns the name of this Sneaker
    @Override
    public String toString() {
        return getName();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("purchased", purchasedPrice);
        json.put("type", type);
        return json;
    }

    public String getImagePath() {
        return type.getImagePath();
    }
}
