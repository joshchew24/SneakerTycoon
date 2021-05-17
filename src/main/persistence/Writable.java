package persistence;

import org.json.JSONObject;

// referenced JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
