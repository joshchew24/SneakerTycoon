package persistence;

import model.Business;
import model.Clicker;
import model.exception.SneakerTypeNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Referenced JsonSerializationDemo
 */
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Clicker c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderInvalidData() {
        JsonReader reader = new JsonReader("./data/testReaderBroken.json");
        try {
            Clicker c = reader.read();
            fail("SneakerTypeNotFoundException should be thrown");
        } catch (IOException e) {
            fail("File exists, no IOException should be thrown");
        } catch (SneakerTypeNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testReaderBrandNewGame() {
        JsonReader reader = new JsonReader("./data/testReaderBrandNewGame.json");
        try {
            Clicker c = reader.read();
            assertEquals(1, c.getGainedPerClick());
            assertEquals(100, c.getUpgradeClickCost());
            Business b = c.getMyBusiness();
            assertEquals("Brand New Business", b.getBusinessName());
            assertEquals(0, b.getBalance());
            assertEquals(0, b.getCollection().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Clicker c = reader.read();
            assertEquals(3, c.getGainedPerClick());
            assertEquals(300, c.getUpgradeClickCost());
            Business b = c.getMyBusiness();
            assertEquals("Chew's Shoes", b.getBusinessName());
            assertEquals(67, b.getBalance());
            assertEquals(3, b.getCollection().size());
            checkSneaker(584.8893f, "Air Jordan 11", b.getCollection().get(0));
            checkSneaker(115.15182f, "Air Force 1", b.getCollection().get(1));
            checkSneaker(158.89124f, "Air Max 90", b.getCollection().get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

}
