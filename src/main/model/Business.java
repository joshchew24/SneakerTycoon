package model;

import model.sneakers.Sneaker;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents the business owned by the player
 */

public class Business implements Writable {

    private final String businessName;
    private final List<Sneaker> collection;

    private float balance;

    private static final int MAX_COLLECTION_SIZE = 10;

    public Business(String name) {
        businessName = name;
        balance = 0;
        collection = new ArrayList<>();
    }


    // transaction methods

    // MODIFIES: this
    // EFFECTS: if this has enough money and room in the collection, pays for sneaker and adds it to collection,
    //          then returns true. Otherwise returns false
    public boolean buy(Sneaker s) {
        if (collection.size() < MAX_COLLECTION_SIZE) {
            if (balance >= s.getMarketPrice()) {
                s.setPurchasedPrice(s.getMarketPrice());
                collection.add(s);
                balance -= s.getMarketPrice();
                s.setIsPurchased(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    // MODIFIES: this
    // EFFECTS: if i - 1 is within collection index, take the sneaker at that index and sell it. selling it will add
    //          it's market value to your balance and removes it from your collection. Otherwise returns null.
    public Sneaker sell(int i) {
        if (i <= collection.size()) {
            Sneaker s = collection.get(i - 1);
            balance += s.getMarketPrice();
            collection.remove(s);
            s.setIsPurchased(false);
            return s;
        } else {
            return null;
        }
    }


    // MODIFIES: this
    // EFFECTS: randomizes market prices for all sneakers in collection
    public void randomizeCollectionMarketPrices() {
        for (Sneaker s : collection) {
            s.randomizeMarketPrice();
        }
    }

    // EFFECTS: returns a list of sneaker information in string form for the entire collection
    public List<String> getCollectionInfo() {
        List<String> collectionInfo = new ArrayList<>();
        for (Sneaker s : collection) {
            collectionInfo.add(s.getInfo() + "\n");
        }
        return collectionInfo;
    }

//    // currently unused
//    // return information about i sneakers of collection, in order stored
//    public List<String> getCollectionInfo(int i) {
//        int tracker = collection.size();
//        List<String> collectionInfo = new ArrayList<>();
//        while (!(tracker == collection.size() - i)) {
//            for (Sneaker s : collection) {
//                collectionInfo.add(s.getInfo() + "\n");
//                tracker--;
//            }
//        }
//        return collectionInfo;
//    }


    // getters:
    public String getBusinessName() {
        return businessName;
    }

    public float getBalance() {
        return balance;
    }

    public List<Sneaker> getCollection() {
        return collection;
    }

    public static int getMaxCollectionSize() {
        return MAX_COLLECTION_SIZE;
    }


    // setters:
    public void setBalance(float balance) {
        this.balance = balance;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", businessName);
        json.put("money", balance);
        json.put("collection", collectionToJson());
        return json;
    }

    // EFFECTS: returns sneaker collection as a JSONArray of JSONObjects
    private JSONArray collectionToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Sneaker s : collection) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
