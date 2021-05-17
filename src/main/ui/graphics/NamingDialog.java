package ui.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*
 * Represents the dialog popup that shows when starting a new game.
 */
public class NamingDialog extends JDialog {

    private SneakerTycoonGUI main;
    private boolean complete;
    private String businessName;

    private JTextField nameField;

    public NamingDialog(SneakerTycoonGUI main) {
        super(main.frame);
        this.main = main;
        initializeNamingDialogGraphics();
        complete = false;

        nameField = new JTextField();
        nameField.setFont(main.getFont(24));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setText("YOUR BUSINESS NAME");

        JPanel buttonPanel = setupButtonPanel();

        this.add(nameField);
        this.add(buttonPanel);
        this.addWindowListener(new CloseAction());

        this.setVisible(true);
    }

    // EFFECTS: helper that sets up the button panel to confirm or cancel starting a new game
    private JPanel setupButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton confirm = new JButton();
        confirm.setPreferredSize(new Dimension(150, 40));
        confirm.setText("PLAY");
        confirm.setFont(main.getFont(24));
        confirm.addActionListener(new ConfirmAction());
        buttonPanel.add(confirm);

        JButton cancel = new JButton();
        cancel.setPreferredSize(new Dimension(180, 40));
        cancel.setText("CANCEL");
        cancel.setFont(main.getFont(24));
        cancel.addActionListener(new CloseAction());
        buttonPanel.add(cancel);

        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics
    private void initializeNamingDialogGraphics() {
        this.setMinimumSize(new Dimension(500, 150));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLayout(new GridLayout(0, 1));
        this.setLocationRelativeTo(null);
        this.setTitle("NAME YOUR BUSINESS");

    }

    // EFFECTS: returns true if the naming process is complete and can proceed to the game
    public boolean isComplete() {
        return complete;
    }

    // EFFECTS: getter method for businessName
    public String getBusinessName() {
        return businessName;
    }



    /*
     * Represents action to be taken when user confirms their desired name
     */
    private class ConfirmAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nameField.getText().equals("")) {
                businessName = nameField.getText().toUpperCase();
                complete = true;
                dispose();
            } else if (nameField.getText().equals("")) {
                nameField.setText("YOUR BUSINESS NEEDS A NAME");
                complete = false;
            }

        }
    }

    /*
     * Represents action to be taken when user wants to close the dialog
     */
    private class CloseAction implements WindowListener, ActionListener {

        @Override
        public void windowClosing(WindowEvent e) {
            close();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            close();
        }

        // MODIFIES: this
        // EFFECTS: sets complete to false and disposes the dialog
        private void close() {
            complete = false;
            dispose();
        }

        @Override
        public void windowOpened(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // do nothing
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // do nothing
        }


    }
}
