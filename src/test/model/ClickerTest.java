package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClickerTest {

    Business testBusiness;
    Clicker testClicker;

    // initializes a business and a clicker tool for that business
    //  tests will only have direct access to testClicker, and can access testBusiness through a getter
    @BeforeEach
    public void setup() {
        testBusiness = new Business("Test E Co.");
        testClicker = new Clicker(testBusiness);
    }

    // tests that the clicker is constructed correctly
    @Test
    public void testConstructor() {
        assertEquals(100, testClicker.getUpgradeClickCost());
        assertEquals(1, testClicker.getGainedPerClick());
    }


    // tests clicking when not upgraded
    @Test
    public void testClickFewNoUpgrades() {
        Business bus = testClicker.getMyBusiness();
        assertEquals(0, testBusiness.getBalance());
        testClicker.click();
        assertEquals(1, bus.getBalance());
        testClicker.click();
        assertEquals(2, bus.getBalance());
        testClicker.click();
        assertEquals(3, bus.getBalance());
        testClicker.click();
        assertEquals(4, bus.getBalance());
    }

    // tests clicking with multiple upgrades
    @Test
    public void testClickFewFewUpgrades() {
        Business bus = testClicker.getMyBusiness();
        bus.setBalance(600);
        testClicker.upgradeClick();
        testClicker.upgradeClick();
        testClicker.upgradeClick();

        assertEquals(0, bus.getBalance());
        testClicker.click();
        assertEquals(4, bus.getBalance());
        testClicker.click();
        assertEquals(8, bus.getBalance());
        testClicker.click();
        assertEquals(12, bus.getBalance());
        testClicker.click();
        assertEquals(16, bus.getBalance());
    }


    // tests case where you cannot afford an upgrade
    @Test
    public void testUpgradeClickCantAfford() {
        Business bus = testClicker.getMyBusiness();
        bus.setBalance(50);
        assertEquals(100, testClicker.getUpgradeClickCost());

        assertFalse(testClicker.upgradeClick());
        assertEquals(50, bus.getBalance());
        assertEquals(100, testClicker.getUpgradeClickCost());
    }

    // tests case where you can afford one upgrade
    @Test
    public void testUpgradeClickOnce() {
        Business bus = testClicker.getMyBusiness();
        bus.setBalance(100);
        assertEquals(100, testClicker.getUpgradeClickCost());

        assertTrue(testClicker.upgradeClick());
        assertEquals(0, bus.getBalance());
        assertEquals(200, testClicker.getUpgradeClickCost());

        assertFalse(testClicker.upgradeClick());
        assertEquals(0, bus.getBalance());
        assertEquals(200, testClicker.getUpgradeClickCost());
    }

    // tests case where you can afford multiple upgrades
    @Test
    public void testUpgradeClickFew() {
        Business bus = testClicker.getMyBusiness();
        bus.setBalance(600);
        assertEquals(100, testClicker.getUpgradeClickCost());

        assertTrue(testClicker.upgradeClick());
        assertEquals(500, bus.getBalance());
        assertEquals(200, testClicker.getUpgradeClickCost());

        assertTrue(testClicker.upgradeClick());
        assertEquals(300, bus.getBalance());
        assertEquals(300, testClicker.getUpgradeClickCost());

        assertTrue(testClicker.upgradeClick());
        assertEquals(0, bus.getBalance());
        assertEquals(400, testClicker.getUpgradeClickCost());

        assertFalse(testClicker.upgradeClick());
        assertEquals(0, bus.getBalance());
        assertEquals(400, testClicker.getUpgradeClickCost());

    }

}
