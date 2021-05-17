package model.sneakers;

import model.exception.SneakerTypeNotFoundException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/*
 * Represents different types of sneakers.
 */

public enum SneakerType {
    STAN_SMITH("Stan Smith", 110, Rarity.COMMON, "stansmith.png"),
    SUPERSTAR("Superstar", 100, Rarity.COMMON, "superstar.png"),
    AIR_FORCE_1("Air Force 1", 120, Rarity.COMMON, "airforce1.png"),

    AIR_MAX_1("Air Max 1", 170, Rarity.UNCOMMON, "airmax1.png"),
    AIR_MAX_90("Air Max 90", 160, Rarity.COMMON, "airmax90.png"),
    AIR_MAX_97("Air Max 97", 225, Rarity.UNCOMMON, "airmax97.png"),
    NMD_R1("NMD R1", 170, Rarity.UNCOMMON, "nmdr1.png"),
    ULTRABOOST_1("Ultraboost 1.0", 250, Rarity.UNCOMMON, "ultraboost.png"),

    AIR_JORDAN_1("Air Jordan 1", 225, Rarity.RARE, "jordan1.png"),
    AIR_JORDAN_3("Air Jordan 3", 265, Rarity.RARE, "jordan3.png"),
    AIR_JORDAN_4("Air Jordan 4", 255, Rarity.RARE, "jordan4.png"),
    AIR_JORDAN_11("Air Jordan 11", 295, Rarity.RARE, "jordan11.png"),
    YEEZY_350("Yeezy 350", 300, Rarity.RARE, "yeezy350.png"),

    SW_97_1("Sean Wotherspoon Air Max 1/97", 210, Rarity.GRAIL, "sw97-1.png"),
    CHICAGO_AIR_JORDAN_1("2015 Chicago Air Jordan 1", 225, Rarity.GRAIL, "2015chicago.png"),

    RED_OCTOBER_YEEZY("Nike Air Yeezy 2 Red October", 330, Rarity.LEGENDARY, "redoctober.png"),
    MAG_BTTF("2016 Nike Mags Back to the Future", 1000, Rarity.LEGENDARY, "airmag.png");


    private final String label;
    private final float retailPrice;
    private final Rarity rarity;
    private final String imageName;
    private static final String DIRECTORY = "./data/assets/";

    // enum constructor
    // EFFECTS: initializes sneaker type with label, retailPrice, marketPrice, rarity
    SneakerType(String label, float retailPrice, Rarity rarity, String imageName) {
        this.label = label;
        this.retailPrice = retailPrice;
        this.rarity = rarity;
        this.imageName = imageName;
    }


    // EFFECTS: gets SneakerType at index i from given EnumSet
    public static SneakerType get(EnumSet<SneakerType> setOfSneakerTypes, int i) {
        List<SneakerType> workingList = new ArrayList<>(setOfSneakerTypes);
        return workingList.get(i);
    }

    // EFFECTS: returns an EnumSet containing all SneakerTypes
    public static EnumSet<SneakerType> getAllSneakerTypes() {
        return EnumSet.allOf(SneakerType.class);
    }

    // EFFECTS: returns an EnumSet with all SneakerTypes with given rarity
    public static EnumSet<SneakerType> getAllOfRarity(Rarity rarity) {
        // instantiates empty EnumSet
        EnumSet<SneakerType> result = EnumSet.noneOf(SneakerType.class);
        EnumSet<SneakerType> allSneakerTypes = getAllSneakerTypes();
        for (SneakerType s : allSneakerTypes) {
            if (s.getRarity().equals(rarity)) {
                result.add(s);
            }
        }
        return result;
    }

    // returns SneakerType that matches given name
    // throws SneakerTypeNotFoundException if name does not match name of any SneakerTypes in enum
    //  (this should never occur... getType is only called with names that are taken from the JSON file which is created
    //   with SneakerTypes that are known to exist)
    public static SneakerType getType(String name) {
        for (SneakerType s : getAllSneakerTypes()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        throw new SneakerTypeNotFoundException(name + " is not a valid sneaker type.");
    }


    // getters:

    public float getRetailPrice() {
        return retailPrice;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name();
    }

    public String getImagePath() {
        return DIRECTORY + imageName;
    }
}