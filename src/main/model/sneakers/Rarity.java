package model.sneakers;

/*
 * Represents different possible rarities of sneakers
 */

public enum Rarity {

    COMMON(0.6999),
    UNCOMMON(0.25),
    RARE(0.045),
    GRAIL(0.005),
    LEGENDARY(0.0001);

    private final double dropChance;

    // enum constructor
    // EFFECTS: initializes rarity with chance of appearing in market
    Rarity(double dropChance) {
        this.dropChance = dropChance;
    }

    // getter:
    public double getDropChance() {
        return this.dropChance;
    }

}

