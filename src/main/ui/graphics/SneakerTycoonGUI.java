package ui.graphics;

import model.Business;
import model.Clicker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Represents the main frame and game GUI
 */
public class SneakerTycoonGUI {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 720;
    private static final String FONT_NAME = "Helvetica";
    public static final String JSON_STORE = "./data/game.json";

    private Business myBusiness;
    private Clicker clicker;

    public JFrame frame;
    private JPanel gamePanel;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JButton clickerButton;
    private JButton upgradeButton;
    private JButton refreshButton;
    private TransactionPanel transactionPanel;

    public SneakerTycoonGUI() {
        frame = new JFrame("SNEAKER TYCOON");
        runStartMenu();
        addMenu();
        runGamePanel();
    }

    // MODIFIES: this
    // EFFECTS: runs the starting menu
    private void runStartMenu() {
        StartPanel startPanel = new StartPanel(this);
        frame.add(startPanel);
        initializeGraphics();
        while (startPanel.isRunning()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        myBusiness = startPanel.business;
        clicker = startPanel.clicker;
        frame.remove(startPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds menu to the game panel
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
//        fileMenu.setMnemonic('S');
        addMenuItem(fileMenu, "Save", new SaveAction());
        addMenuItem(fileMenu, "Load last save", new LoadAction());
        menuBar.add(fileMenu);

        // TODO: add a help menu

        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: menu
    // EFFECTS: adds a menu item with given name and action to given menu
    private void addMenuItem(JMenu menu, String name, ActionListener action) {
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(name);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    // MODIFIES: this
    // EFFECTS: runs the main game
    private void runGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
        setupInfo();
        setupButtons();
        transactionPanel = new TransactionPanel(this);
        transactionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCenterOfGamePanel(transactionPanel);

        frame.add(gamePanel);
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: sets up name and balance labels
    private void setupInfo() {
        nameLabel = new JLabel(myBusiness.getBusinessName());
        nameLabel.setFont(getFont(100));

        balanceLabel = new JLabel("BALANCE: $" + myBusiness.getBalance());
        balanceLabel.setFont(getFont(25));
        addToCenterOfGamePanel(nameLabel);
        addToCenterOfGamePanel(balanceLabel);
    }

    // MODIFIES: this
    // EFFECTS: sets up click, upgrade, and refresh buttons
    private void setupButtons() {
        clickerButton = new JButton("GAIN $" + clicker.getGainedPerClick() + "!");
        clickerButton.addActionListener(new ClickAction());
        clickerButton.setPreferredSize(new Dimension(WIDTH, 170));
        clickerButton.setFont(getFont(30));
        addToCenterOfGamePanel(clickerButton);

        upgradeButton = new JButton("UPGRADE TO $" + (clicker.getGainedPerClick() + 1) + "\n"
                + " (COSTS $" + clicker.getUpgradeClickCost() + ")");
        upgradeButton.addActionListener(new UpgradeAction());
        upgradeButton.setFont(getFont(20));
        addToCenterOfGamePanel(upgradeButton);

        refreshButton = new JButton("REFRESH MARKETPLACE");
        refreshButton.addActionListener(new RefreshAction());
        refreshButton.setFont(getFont(20));
        addToCenterOfGamePanel(refreshButton);

    }

    // MODIFIES: this, c
    // EFFECTS: adds c to the center of the gamePanel
    private void addToCenterOfGamePanel(JComponent c) {
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(c);
    }

    // MODIFIES: this
    // EFFECTS: initializes main frame graphics
    private void initializeGraphics() {
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        centreOnScreen();
    }

    // MODIFIES: this
    // EFFECTS: helper to place the main frame in the centre of the screen
    private void centreOnScreen() {
        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: updates all panel information
    public void update() {
        nameLabel.setText(myBusiness.getBusinessName().toUpperCase());
        balanceLabel.setText("BALANCE: $" + myBusiness.getBalance());
        upgradeButton.setText("UPGRADE TO $" + (clicker.getGainedPerClick() + 1) + "\n"
                + " (COSTS $" + clicker.getUpgradeClickCost() + ")");
        clickerButton.setText("GAIN $" + clicker.getGainedPerClick());
        transactionPanel.update();
    }

    // EFFECTS: helper method for returning a Font object of given size in default font
    public Font getFont(int size) {
        return new Font(FONT_NAME, Font.BOLD, size);
    }

    // EFFECTS: getter method for myBusiness
    public Business getBusiness() {
        return myBusiness;
    }

    /*
     * Represents the action to be taken when the user wants to gain money through clicking
     */
    private class ClickAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clicker.click();
            update();
        }
    }

    /*
     * Represents the action to be taken when the user wants to upgrade their clicking power
     */
    private class UpgradeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clicker.upgradeClick();
            update();
        }
    }

    /*
     * Represents the action to be taken when the user wants to refresh the marketplace listings and prices
     */
    private class RefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            transactionPanel.refreshMarket();
        }
    }

    /*
     * Represents action to be taken when user wants to save their game progress
     */
    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Referenced JsonSerializationDemo
            // EFFECTS: saves game state to file
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(clicker);
                jsonWriter.close();
                System.out.println("Saved game state to " + JSON_STORE);
            } catch (FileNotFoundException f) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    /*
     * Represents action to be taken when user wants to load their saved game progress
     */
    private class LoadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            try {
                clicker = jsonReader.read();
                myBusiness = clicker.getMyBusiness();
                update();
                System.out.println("Loaded " + myBusiness.getBusinessName() + " from " + JSON_STORE);
            } catch (IOException i) {
                update();
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}
