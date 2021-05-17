package model;

import model.sneakers.Sneaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.sneakers.Rarity.*;

/*
 * Represents a marketplace for buying sneakers.
 */

public class Marketplace {

    public int numListings = 5;

    double grailDropThreshold = LEGENDARY.getDropChance() + GRAIL.getDropChance();
    double rareDropThreshold = grailDropThreshold + RARE.getDropChance();
    double uncommonDropThreshold = rareDropThreshold + UNCOMMON.getDropChance();

    List<Sneaker> listings;

    // EFFECTS: constructs a marketplace with 5 randomized listings
    public Marketplace() {
        listings = new ArrayList<>();
        generateNewListings();
    }

    // MODIFIES: this
    // EFFECTS: generates 5 random Sneakers to add to listings
    public void generateNewListings() {
        Sneaker s;

        listings.clear();

        // rng is a double from 0.0 to 1.0. a sneaker is generated based on which threshold rng falls under
        for (int i = 1; i <= numListings; i++) {
            double rng = randomNumberGenerator();
            if (rng <= LEGENDARY.getDropChance()) {
                s = new Sneaker(LEGENDARY);
                listings.add(s);
            } else if (rng <= grailDropThreshold) {
                s = new Sneaker(GRAIL);
                listings.add(s);
            } else if (rng <= rareDropThreshold) {
                s = new Sneaker(RARE);
                listings.add(s);
            } else if (rng <= uncommonDropThreshold) {
                s = new Sneaker(UNCOMMON);
                listings.add(s);
            } else {
                s = new Sneaker(COMMON);
                listings.add(s);
            }
        }
    }

    // EFFECTS: returns information for all current listings as a list of strings
    public List<String> getListingsInfo() {
        List<String> listingInfo = new ArrayList<>();
        for (Sneaker s : listings) {
            listingInfo.add(s.getInfo() + "\n");
        }
        return listingInfo;
    }

    // EFFECTS: randomly generates and returns a number from 0.0 to 1.0, representing the chance that certain sneakers
    //          appear in the marketplace
    private double randomNumberGenerator() {
        Random rand = new Random();
        return rand.nextDouble();
    }

    // getter:
    public List<Sneaker> getListings() {
        return listings;
    }


}
