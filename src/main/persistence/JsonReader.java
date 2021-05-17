package persistence;

import model.Business;
import model.Clicker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.sneakers.Sneaker;
import model.sneakers.SneakerType;
import org.json.*;

/*
 * Referenced JsonSerializationDemo
 * Represents a reader that reads clicker from JSON data stored in file
 */
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Clicker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Clicker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseClicker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Clicker from JSON object and returns it
    private Clicker parseClicker(JSONObject jsonObject) {
        Business business = parseBusiness(jsonObject);
        Clicker c = new Clicker(business);
        c.setGainedPerClick(jsonObject.getFloat("click gain"));
        c.setUpgradeCost(jsonObject.getFloat("upgrade cost"));
        return c;
    }

    // EFFECTS: parses business from JSON object and returns it
    private Business parseBusiness(JSONObject jsonObject) {
        JSONObject jsonBusiness = jsonObject.getJSONObject("business");
        Business business = new Business((String) jsonBusiness.get("name"));
        business.setBalance(jsonBusiness.getFloat("money"));
        parseCollection(business, jsonBusiness.getJSONArray("collection"));
        return business;
    }

    // MODIFIES: business
    // EFFECTS: parses collection from JSON object and adds each sneaker to the business' collection
    private void parseCollection(Business business, JSONArray collection) {
        for (Object o : collection) {
            JSONObject jsonSneaker = (JSONObject) o;
            Sneaker s = parseSneaker(jsonSneaker);
            business.getCollection().add(s);
        }
    }

    // EFFECTS: parses sneaker from JSON object and returns it
    private Sneaker parseSneaker(JSONObject o) {
        String name = o.getString("type");
        SneakerType type = SneakerType.getType(name);
        Sneaker s = new Sneaker(type);
        s.setIsPurchased(true);
        s.setPurchasedPrice(o.getFloat("purchased"));
        return s;
    }
}
