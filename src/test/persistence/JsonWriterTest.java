package persistence;

import model.Business;
import model.Clicker;
import model.sneakers.Sneaker;
import model.sneakers.SneakerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Referenced JsonSerializationDemo
 */
public class JsonWriterTest extends JsonTest {

    Business b;
    Clicker c;
    Sneaker s1;
    Sneaker s2;
    Sneaker s3;

    @BeforeEach
    public void setup() {
        b = new Business("Random Test Business");
        c = new Clicker(b);
        s1 = new Sneaker(SneakerType.CHICAGO_AIR_JORDAN_1);
        s2 = new Sneaker(SneakerType.NMD_R1);
        s3 = new Sneaker(SneakerType.AIR_MAX_97);
        s1.setMarketPrice(3000);
        s2.setMarketPrice(160);
        s3.setMarketPrice(240);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterBrandNewGame.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBrandNewGame.json");
            c = reader.read();
            assertEquals(1, c.getGainedPerClick());
            assertEquals(100, c.getUpgradeClickCost());
            b = c.getMyBusiness();
            assertEquals("Random Test Business", b.getBusinessName());
            assertEquals(0, b.getBalance());
            assertEquals(0, b.getCollection().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            setupGeneral();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            c = reader.read();
            assertEquals(5, c.getGainedPerClick());
            assertEquals(500, c.getUpgradeClickCost());
            b = c.getMyBusiness();
            assertEquals("Random Test Business", b.getBusinessName());
            assertEquals(1600, b.getBalance());
            assertEquals(3, b.getCollection().size());
            checkSneaker(3000, "2015 Chicago Air Jordan 1", b.getCollection().get(0));
            checkSneaker(160, "NMD R1", b.getCollection().get(1));
            checkSneaker(240, "Air Max 97", b.getCollection().get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up clicker and business to have some testable values
    private void setupGeneral() {
        c.setGainedPerClick(5);
        c.setUpgradeCost(500);
        b.setBalance(5000);
        b.buy(s1);
        b.buy(s2);
        b.buy(s3);
    }

}
