package ui.graphics;

import model.Business;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.Clicker;
import persistence.JsonReader;

/*
 * Represents starting menu to start a new game or continue the previous game
 */
public class StartPanel extends JPanel {

    private SneakerTycoonGUI main;
    private NamingDialog namingDialog;

    private boolean running;
    private Font buttonFont;
    public Business business;
    public Clicker clicker;

    public StartPanel(SneakerTycoonGUI main) {
        this.main = main;
        running = true;
        setLayout(new GridLayout(0, 1));
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets up new game and continue buttons
    private void initializeButtons() {
        // TODO: setFocusPainted(false) for all buttons
        buttonFont = main.getFont(72);
        JButton newGame = new JButton("\" NEW GAME \"");
        newGame.setFont(buttonFont);
        newGame.addActionListener(new NewGameAction());
        this.add(newGame);

        JButton continueGame = new JButton("CONTINUE");
        continueGame.setFont(buttonFont);
        continueGame.addActionListener(new ContinueGameAction());
        this.add(continueGame);

        // TODO: add more buttons? maybe multiple saves?
        JButton help = new JButton("Help");
        JButton quit = new JButton("Quit");
    }


    //Shows loading screen until SneakerTycoonGUI checks if complete is true
    private void transitionToGame() {
        this.removeAll();
        JLabel loadingText = new JLabel("Loading...");
        loadingText.setFont(main.getFont(150));
        loadingText.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(loadingText);
        this.revalidate();
        this.repaint();
        running = false;
    }


    // EFFECTS: returns true if start menu is still running
    public boolean isRunning() {
        return running;
    }


    /*
     * Represents action to be taken when user wants to start a new game
     */
    private class NewGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: popup asking for name?
            namingDialog = new NamingDialog(main);
            business = new Business(namingDialog.getBusinessName());
            clicker = new Clicker(business);
            if (namingDialog.isComplete()) {
                transitionToGame();
            }
        }
    }

    /*
     * Represents action to be taken when user wants to continue their progress from a previous session
     */
    private class ContinueGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JsonReader jsonReader = new JsonReader(main.JSON_STORE);
            try {
                clicker = jsonReader.read();
                business = clicker.getMyBusiness();
                System.out.println("Loaded " + business.getBusinessName() + " from " + main.JSON_STORE);
            } catch (IOException i) {
                System.out.println("Unable to read from file: " + main.JSON_STORE);
                business = new Business("xd");
            }
            transitionToGame();
        }
    }



}
