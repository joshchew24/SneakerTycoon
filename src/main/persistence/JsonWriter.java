package persistence;

import model.Clicker;
import org.json.JSONObject;

import java.io.*;

/*
 * referenced JsonSerializationDemo
 * Represents a writer that writes JSON representation of Clicker to file
 * note: saves the clicker because it encapsulates the business which we also need to save
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }
    
    // MODIFIES: this
    // EFFECTS: writes JSON representation of Clicker to file
    public void write(Clicker c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
