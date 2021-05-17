package ui.graphics;

import model.Business;
import model.Marketplace;
import model.sneakers.Sneaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Represents the panel in which a user can buy sneakers from the marketplace and sell from their collection
 */
public class TransactionPanel extends JSplitPane {

    private Business myBusiness;
    private Marketplace marketplace;
    private Sneaker currentSneaker;

    private SneakerTycoonGUI parent;

    private JList<Sneaker> collectionJList;
    private JList<Sneaker> marketplaceJList;
    private DefaultListModel<Sneaker> collectionModel;
    private DefaultListModel<Sneaker> marketModel;
    private JPanel viewerPanel;
    private JLabel sneakerImage;
    private JLabel sneakerInfo;
    private JButton transactionButton;

    private ListSelectionListener collectionViewListener;
    private ListSelectionListener marketViewListener;

    public TransactionPanel(SneakerTycoonGUI parent) {
        myBusiness = parent.getBusiness();
        marketplace = new Marketplace();
        currentSneaker = marketplace.getListings().get(0);
        this.parent = parent;

        marketplaceJList = new JList<>();
        marketModel = new DefaultListModel<>();
        marketplaceJList.setModel(marketModel);
        marketViewListener = new ViewAction();
        marketplaceJList.addListSelectionListener(marketViewListener);

        this.setEnabled(false);
        marketplaceJList.setFont(parent.getFont(24));

        // set splits to their respective components
        this.setLeftComponent(setupInnerSplitPane());
        this.setRightComponent(marketplaceJList);
        this.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: sets up the left and "middle" panels
    private JSplitPane setupInnerSplitPane() {
        // intiialize splitpane and collection jlist
        JSplitPane innerSplitPane = new JSplitPane();
        collectionJList = new JList<>();
        collectionModel = new DefaultListModel<>();
        collectionJList.setModel(collectionModel);
        collectionViewListener = new ViewAction();
        collectionJList.addListSelectionListener(collectionViewListener);


        innerSplitPane.setEnabled(false);
        collectionJList.setFont(parent.getFont(24));
//        collectionJList.setMinimumSize(new Dimension(200, 100));

        updateLists();

        // set left and "middle" pane
        innerSplitPane.setLeftComponent(new JScrollPane(collectionJList));
        innerSplitPane.setRightComponent(setupMiddlePane());

        return innerSplitPane;
    }

    // MODIFIES: this
    // EFFECTS: sets up middle panel to be viewer panel
    private JPanel setupMiddlePane() {
        // initialize panel
        viewerPanel = new JPanel();
        viewerPanel.setLayout(new BoxLayout(viewerPanel, BoxLayout.PAGE_AXIS));
        viewerPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        viewerPanel.add(setupSneakerImage());
        viewerPanel.add(setupSneakerInfo());
        viewerPanel.add(setupTransactionButton());

//        viewerPanel.setMinimumSize(new Dimension(200, 100));

        return viewerPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up sneaker image
    private JLabel setupSneakerImage() {

        // intialize image label
        sneakerImage = new JLabel();
        updateSneakerImage();
        sneakerImage.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        return sneakerImage;
    }

    // MODIFIES: this
    // EFFECTS: sets up sneaker information
    private JLabel setupSneakerInfo() {
        // initialize JLabel
        sneakerInfo = new JLabel();
        sneakerInfo.setFont(parent.getFont(20));
        updateSneakerInfo();

        // setup JLabel location
        sneakerInfo.setHorizontalAlignment(JLabel.CENTER);
        sneakerInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sneakerInfo.setPreferredSize(new Dimension(300, 100));
        return sneakerInfo;
    }

    // MODIFIES: this
    // EFFECTS: sets up buy/sell button
    private JButton setupTransactionButton() {
        transactionButton = new JButton();
        updateTransactionButton();
        transactionButton.setHorizontalAlignment(JButton.CENTER);
        transactionButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        transactionButton.setFont(parent.getFont(14));

        transactionButton.addActionListener(new TransactionAction());

        return transactionButton;
    }

    // referenced: https://stackoverflow.com/questions/23478984/read-image-from-another-directory
    // EFFECTS: Returns an ImageIcon, or null if the path was invalid.
    private BufferedImage getBufferedImageFromPath(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    // MODIFIES: this
    // EFFECTS: helper for refreshing market
    public void refreshMarket() {
        marketplace.generateNewListings();
        myBusiness.randomizeCollectionMarketPrices();
        updateAll();
    }

    // MODIFIES: this
    // EFFECTS: updates all panels with changing information
    private void updateAll() {
        update();
        parent.update();
    }

    // EFFECTS: updates sneaker viewer panel
    private void updateViewer() {
        updateSneakerImage();
        updateSneakerInfo();
        updateTransactionButton();
    }

    // MODIFIES: this
    // EFFECTS: updates business with parent business, updates lists and sneaker viewer
    public void update() {
        myBusiness = parent.getBusiness();
        updateLists();
        updateViewer();
    }

    // MODIFIES: this
    // EFFECTS: helper for updating JLists
    private void updateLists() {
        collectionJList.removeListSelectionListener(collectionViewListener);
        marketplaceJList.removeListSelectionListener(marketViewListener);

        collectionModel.clear();
        marketModel.clear();
        for (Sneaker s : myBusiness.getCollection()) {
            collectionModel.addElement(s);
        }
        for (Sneaker s : marketplace.getListings()) {
            marketModel.addElement(s);
        }

        collectionViewListener = new ViewAction();
        marketViewListener = new ViewAction();
        collectionJList.addListSelectionListener(collectionViewListener);
        marketplaceJList.addListSelectionListener(marketViewListener);
    }

    // MODIFIES: this
    // EFFECTS: helper for updating sneaker image
    private void updateSneakerImage() {
        sneakerImage.setSize(new Dimension(150, 150));

        // get unscaled image from file path
        BufferedImage unscaledImage = getBufferedImageFromPath(currentSneaker.getImagePath());
        Image scaledImage = unscaledImage.getScaledInstance(sneakerImage.getWidth(), sneakerImage.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        sneakerImage.setIcon(imageIcon);
    }

    // MODIFIES: this
    // EFFECTS: helper for updating sneaker information
    private void updateSneakerInfo() {
        // setup text to put in jlabel
        StringBuilder infoText = new StringBuilder("<html>"
                + "Name: " + currentSneaker.getName() + "<br>"
                + "Rarity: " + currentSneaker.getRarity() + "<br>"
                + "Retail Price: $" + currentSneaker.getRetailPrice() + "<br>");
        if (currentSneaker.getIsPurchased()) {
            infoText.append("Purchased Price: $" + currentSneaker.getPurchasedPrice());
        }
        infoText.append("</html>");
        sneakerInfo.setText(String.valueOf(infoText));
    }

    // MODIFIES: this
    // EFFECTS: helper for updating transaction button
    private void updateTransactionButton() {
        if (currentSneaker.getIsPurchased()) {
            transactionButton.setText("Sell for $" + currentSneaker.getMarketPrice());
        } else {
            transactionButton.setText("Buy for $" + currentSneaker.getMarketPrice());
        }
    }


    /*
     * Represents action taken when user wants to view a sneaker from a list
     */
    private class ViewAction implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                JList<Sneaker> currentList = (JList) e.getSource();
                // TODO: only have one list selected at a time
//                if (currentList.equals(marketplaceJList)) {
//                    collectionJList.clearSelection();
//                } else {
//                    marketplaceJList.clearSelection();
//                }
                currentSneaker = currentList.getSelectedValue();
                System.out.println(currentSneaker.getInfo());

                invalidate();
                updateViewer();
                viewerPanel.repaint();
            }
        }
    }

    /*
     * Represents action taken when user wants to buy/sell a sneaker from a list
     */
    private class TransactionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentSneaker.getIsPurchased()) {
                // TODO: show the popup
                // sell the sneaker at index
                // TODO: sneaker index should be different... selling at i + 1 for console UI right now
                myBusiness.sell(myBusiness.getCollection().indexOf(currentSneaker) + 1);
                updateAll();
                marketplaceJList.setSelectedIndex(0);
            } else {
                if (myBusiness.buy(currentSneaker)) {
                    marketplace.getListings().remove(currentSneaker);
                    updateAll();
                    collectionJList.setSelectedValue(currentSneaker, true);
                }
                // TODO: else print error
            }
            // TODO: play a sound?
        }
    }
}
