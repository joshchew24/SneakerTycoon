package model;

/*
 * Tool for handling clicking behaviour and upgrades
 */

import org.json.JSONObject;
import persistence.Writable;

public class Clicker implements Writable {

    private float gainedPerClick;
    private float upgradeCost;

    private final Business myBusiness;

    private static final float UPGRADE_COST_INCREMENT = 100;
    private static final float UPGRADE_INCREMENT = 1;

    // EFFECTS: constructs a tool that allows you to click to earn money
    public Clicker(Business business) {
        gainedPerClick = 1;
        upgradeCost = 100;
        myBusiness = business;
    }

    // MODIFIES: this
    // EFFECTS: increases your balance by  gainedPerClick
    public void click() {
        myBusiness.setBalance(myBusiness.getBalance() + gainedPerClick);
    }

    // MODIFIES: this
    // EFFECTS: if you can afford upgradeCost, increase gainedPerClick by UPGRADE_INCREMENT, pay upgradeCost,
    //          upgradeCost gets more expensive by UPGRADE_COST_INCREMENT, and return true. otherwise return false.
    public boolean upgradeClick() {
        if (myBusiness.getBalance() >= upgradeCost) {
            setGainedPerClick(gainedPerClick + UPGRADE_INCREMENT);
            myBusiness.setBalance(myBusiness.getBalance() - upgradeCost);
            setUpgradeCost(upgradeCost + UPGRADE_COST_INCREMENT);
            return true;
        } else {
            return false;
        }
    }


    // getters:

    public float getUpgradeClickCost() {
        return upgradeCost;
    }

    public float getGainedPerClick() {
        return gainedPerClick;
    }

    public Business getMyBusiness() {
        return myBusiness;
    }


    // setters:

    public void setGainedPerClick(float gainedPerClick) {
        this.gainedPerClick = gainedPerClick;
    }

    public void setUpgradeCost(float upgradeClickCost) {
        this.upgradeCost = upgradeClickCost;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("business", myBusiness.toJson());
        json.put("click gain", gainedPerClick);
        json.put("upgrade cost", upgradeCost);
        return json;
    }
}
