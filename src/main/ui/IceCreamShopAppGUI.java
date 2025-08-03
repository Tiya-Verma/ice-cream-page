package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Graphical User Interface for the Ice Cream Shop Pre-Order System
 */
public class IceCreamShopAppGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/orderqueue.json";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private OrderQueue orderQueue;
    private List<Flavor> availableFlavors;
    private List<Topping> availableToppings;
    private List<Size> availableSizes;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // GUI Components
    private JPanel orderDisplayPanel;
    private JScrollPane orderScrollPane;
    private JTextField customerNameField;
    private JComboBox<Flavor> flavorComboBox;
    private JComboBox<Size> sizeComboBox;
    private JList<Topping> toppingsList;
    private JLabel statusLabel;
    private JToggleButton filterToggle;
    private boolean showOnlyPending = false;

    /**
     * EFFECTS: creates and displays the GUI for the ice cream shop
     */
    public IceCreamShopAppGUI() {
        super("Ice Cream Shop Pre-Order System");
        initializeData();
        initializeGraphics();
        setupComponents();
        setVisible(true);
    }

    /**
     * MODIFIES: this
     * EFFECTS: initializes the application data (order queue, flavors, toppings,
     * sizes)
     */
    private void initializeData() {
        orderQueue = new OrderQueue();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Initialize available flavors
        availableFlavors = new ArrayList<>();
        availableFlavors.add(new Flavor("Vanilla", 3.50));
        availableFlavors.add(new Flavor("Chocolate", 4.00));
        availableFlavors.add(new Flavor("Strawberry", 3.75));
        availableFlavors.add(new Flavor("Mint Chocolate Chip", 4.25));
        availableFlavors.add(new Flavor("Cookie Dough", 4.50));

        // Initialize available toppings
        availableToppings = new ArrayList<>();
        availableToppings.add(new Topping("Sprinkles", 0.50));
        availableToppings.add(new Topping("Marshmallow", 1.00));
        availableToppings.add(new Topping("Chocolate Drizzle", 0.75));
        availableToppings.add(new Topping("Berries", 0.25));
        availableToppings.add(new Topping("Waffle", 0.50));

        // Initialize available sizes
        availableSizes = new ArrayList<>();
        availableSizes.add(new Size("Small", 1.0));
        availableSizes.add(new Size("Medium", 1.25));
        availableSizes.add(new Size("Large", 1.5));
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets up the basic window properties
     */
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    /**
     * MODIFIES: this
     * EFFECTS: sets up and arranges all GUI components
     */
    private void setupComponents() {
        setupMainPanels();
        setupMenuBar();
        updateOrderDisplay();
        applyThemeColors();

    }

    private void applyThemeColors() {
        Color panelBg = new Color(236, 244, 253); // Light blue
        Color buttonBg = new Color(246, 244, 234); // yellow
        Color buttonText = new Color(90, 45, 30); // Brown

        // Frame
        getContentPane().setBackground(panelBg);

        // Panels
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(panelBg);
                applyColorsToChildren((JPanel) c, panelBg, buttonBg, buttonText);
            }
        }

        // Menu bar (optional)
        if (getJMenuBar() != null) {
            getJMenuBar().setBackground(panelBg);
        }

        // Status label
        if (statusLabel != null) {
            statusLabel.setBackground(panelBg);
            statusLabel.setForeground(buttonText);
        }
    }

    private void applyColorsToChildren(Container container, Color panelBg, Color buttonBg, Color buttonText) {
        for (Component c : container.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(panelBg);
                applyColorsToChildren((Container) c, panelBg, buttonBg, buttonText);
            } else if (c instanceof JButton || c instanceof JToggleButton) {
                c.setBackground(buttonBg);
                c.setForeground(buttonText);
            } else if (c instanceof JScrollPane) {
                c.setBackground(Color.WHITE);
            } else if (c instanceof JLabel) {
                c.setForeground(buttonText);
            } else if (c instanceof JComboBox || c instanceof JList) {
                c.setBackground(Color.WHITE);
            }
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: creates and arranges the main panels of the interface
     */
    private void setupMainPanels() {
        // Create main panels
        JPanel leftPanel = createOrderFormPanel();
        JPanel centerPanel = createOrderDisplayPanel();
        JPanel rightPanel = createActionsPanel();

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Add status bar at bottom
        statusLabel = new JLabel("Ready to take orders!");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);
    }

    /**
     * EFFECTS: creates and returns the order form panel
     */
    private JPanel createOrderFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Create New Order"));
        panel.setPreferredSize(new Dimension(300, HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addCustomerNameSection(panel);
        addFlavorSection(panel);
        addSizeSection(panel);
        addToppingsSection(panel);
        addOrderButton(panel);

        return panel;
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds customer name input section to the panel
     */
    private void addCustomerNameSection(JPanel panel) {
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(customerNameLabel);
        customerNameField = new JTextField(20);
        customerNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        customerNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, customerNameField.getPreferredSize().height));
        customerNameField.setBackground(new Color(246, 244, 234));
        panel.add(customerNameField);
        panel.add(Box.createVerticalStrut(10));
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds flavor selection section to the panel
     */
    private void addFlavorSection(JPanel panel) {
        JLabel flavorLabel = new JLabel("Flavor:");
        flavorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        flavorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(flavorLabel);
        flavorComboBox = new JComboBox<>(availableFlavors.toArray(new Flavor[0]));
        flavorComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        flavorComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, flavorComboBox.getPreferredSize().height));
        panel.add(flavorComboBox);
        panel.add(Box.createVerticalStrut(10));
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds size selection section to the panel
     */
    private void addSizeSection(JPanel panel) {
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(sizeLabel);
        sizeComboBox = new JComboBox<>(availableSizes.toArray(new Size[0]));
        sizeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sizeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, sizeComboBox.getPreferredSize().height));
        panel.add(sizeComboBox);
        panel.add(Box.createVerticalStrut(10));
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds toppings selection section to the panel
     */
    private void addToppingsSection(JPanel panel) {
        JLabel toppingsLabel = new JLabel("Toppings (hold Ctrl/Cmd for multiple):");
        toppingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        toppingsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(toppingsLabel);
        toppingsList = new JList<>(availableToppings.toArray(new Topping[0]));
        toppingsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        toppingsList.setBackground(new Color(246, 244, 234));
        JScrollPane toppingsScroll = new JScrollPane(toppingsList);
        toppingsScroll.setPreferredSize(new Dimension(280, 120));
        toppingsScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(toppingsScroll);
        panel.add(Box.createVerticalStrut(10));
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds the add order button to the panel
     */
    private void addOrderButton(JPanel panel) {
        JButton addOrderButton = new JButton("Add Order");
        addOrderButton.setActionCommand("add_order");
        addOrderButton.addActionListener(this);
        addOrderButton.setBackground(new Color(246, 244, 234));
        addOrderButton.setForeground(new Color(147, 94, 62));
        addOrderButton.setBorderPainted(true);
        addOrderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(addOrderButton);
    }

    /**
     * EFFECTS: creates and returns the order display panel
     */
    private JPanel createOrderDisplayPanel() {
        Color backgroundColor = new Color(202, 221, 241); // blue
        Color scrollAreaColor = new Color(245, 208, 199); 

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Orders"));
        panel.setBackground(backgroundColor);

        // Create the orders display area
        orderDisplayPanel = new JPanel();
        orderDisplayPanel.setLayout(new BoxLayout(orderDisplayPanel, BoxLayout.Y_AXIS));
        orderDisplayPanel.setBackground(backgroundColor);

        orderScrollPane = new JScrollPane(orderDisplayPanel);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScrollPane.getViewport().setBackground(scrollAreaColor); 
        orderScrollPane.setBorder(null); 
        orderScrollPane.setBackground(scrollAreaColor); 

        panel.add(orderScrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * EFFECTS: creates and returns the actions panel
     */
    private JPanel createActionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        panel.setPreferredSize(new Dimension(300, HEIGHT));
        panel.setBackground(new Color(246, 244, 234));

        setupActionButtons(panel);
        addImageSection(panel);

        return panel;
    }

    /**
     * MODIFIES: panel
     * EFFECTS: sets up all action buttons in the panel
     */
    private void setupActionButtons(JPanel panel) {
        Dimension buttonSize = new Dimension(250, 35);

        setupFilterToggle(panel, buttonSize);
        addStyledButton(panel, "Complete Order", "complete_order", buttonSize, 10);
        addStyledButton(panel, "Save Orders", "save", buttonSize, 5);
        addStyledButton(panel, "Load Orders", "load", buttonSize, 10);
    }

    /**
     * MODIFIES: panel
     * EFFECTS: sets up the filter toggle button
     */
    private void setupFilterToggle(JPanel panel, Dimension buttonSize) {
        filterToggle = new JToggleButton("Show Pending Only");
        filterToggle.setActionCommand("toggle_filter");
        filterToggle.addActionListener(this);
        styleButton(filterToggle, buttonSize);
        panel.add(filterToggle);
        panel.add(Box.createVerticalStrut(10));
    }

    /**
     * MODIFIES: panel
     * EFFECTS: creates and adds a styled button with given parameters
     */
    private void addStyledButton(JPanel panel, String text, String command,
            Dimension buttonSize, int spacingAfter) {
        JButton button = new JButton(text);
        button.setActionCommand(command);
        button.addActionListener(this);
        styleButton(button, buttonSize);
        panel.add(button);
        panel.add(Box.createVerticalStrut(spacingAfter));
    }

    /**
     * MODIFIES: button
     * EFFECTS: applies standard styling to a button
     */
    private void styleButton(AbstractButton button, Dimension buttonSize) {
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBackground(new Color(246, 244, 234));
        button.setForeground(new Color(147, 94, 62));
        button.setOpaque(true);
        button.setBorderPainted(true);
    }

    /**
     * MODIFIES: panel
     * EFFECTS: adds the image section to the panel
     */
    private void addImageSection(JPanel panel) {
        panel.add(Box.createVerticalStrut(121));
        ImageIcon iceCreamIcon = createIceCreamIcon();
        JLabel imageLabel = new JLabel(iceCreamIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(100));
    }

    /**
     * EFFECTS: creates an ice cream icon for the visual component
     */
    private ImageIcon createIceCreamIcon() {

        File imageFile = new File("data/icecreamshop.png");
        try {
            // Try using ImageIO for better error handling
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            // Scale the image if it's too large
            int maxSize = 430;
            if (bufferedImage.getWidth() > maxSize || bufferedImage.getHeight() > maxSize) {
                double scale = Math.min((double) maxSize / bufferedImage.getWidth(),
                        (double) maxSize / bufferedImage.getHeight());
                int newWidth = (int) (bufferedImage.getWidth() * scale);
                int newHeight = (int) (bufferedImage.getHeight() * scale);

                Image scaledImage = bufferedImage.getScaledInstance(newWidth, newHeight,
                        Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                return new ImageIcon(bufferedImage);
            }
        } catch (IOException e) {
            ;
        }

        return new ImageIcon(imageFile.getAbsolutePath());
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets up the menu bar with save/load options
     */
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save Order Queue");
        saveItem.setActionCommand("save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load Order Queue");
        loadItem.setActionCommand("load");
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * MODIFIES: this
     * EFFECTS: updates the order display panel with current orders
     */
    private void updateOrderDisplay() {
        orderDisplayPanel.removeAll();
        List<Order> ordersToShow;
        if (showOnlyPending) {
            ordersToShow = orderQueue.getPendingOrders();
            filterToggle.setText("Show All Orders");
        } else {
            ordersToShow = new ArrayList<>();
            ordersToShow.addAll(orderQueue.getPendingOrders());
            ordersToShow.addAll(orderQueue.getCompletedOrders());
            filterToggle.setText("Show Pending Only");
        }
        if (ordersToShow.isEmpty()) {
            JLabel emptyLabel = new JLabel("No orders to display");
            orderDisplayPanel.add(emptyLabel);
        } else {
            for (Order order : ordersToShow) {
                JPanel orderPanel = createOrderPanel(order);
                orderDisplayPanel.add(orderPanel);
                orderDisplayPanel.add(Box.createVerticalStrut(5));
            }
        }
        orderDisplayPanel.revalidate();
        orderDisplayPanel.repaint();
    }

    /**
     * EFFECTS: creates and returns a panel displaying order information
     */
    private JPanel createOrderPanel(Order order) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        panel.setBackground(order.isCompleted() ? Color.WHITE : new Color(251, 239, 239));

        // Order info
        JTextArea infoArea = new JTextArea(order.toString());
        infoArea.setEditable(false);
        infoArea.setBackground(panel.getBackground());
        panel.add(infoArea, BorderLayout.CENTER);

        // Status indicator
        JLabel statusIndicator = new JLabel(order.isCompleted() ? "✓ COMPLETED" : "PENDING...");
        statusIndicator.setForeground(order.isCompleted() ? new Color(139, 87, 59) : new Color(181, 68, 82));
        statusIndicator.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(statusIndicator, BorderLayout.EAST);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "add_order":
                addOrder();
                break;
            case "complete_order":
                completeOrder();
                break;
            case "toggle_filter":
                toggleFilter();
                break;
            case "save":
                saveOrderQueue();
                break;
            case "load":
                loadOrderQueue();
                break;
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds a new order based on form input
     */
    private void addOrder() {
        String customerName = customerNameField.getText().trim();
        if (customerName.isEmpty()) {
            statusLabel.setText("Please enter customer name");
            return;
        }

        Flavor selectedFlavor = (Flavor) flavorComboBox.getSelectedItem();
        Size selectedSize = (Size) sizeComboBox.getSelectedItem();
        List<Topping> selectedToppings = toppingsList.getSelectedValuesList();

        Order newOrder = new Order(customerName, selectedFlavor, selectedSize, "ASAP", 5);

        // Add selected toppings
        for (Topping topping : selectedToppings) {
            newOrder.addTopping(topping);
        }

        orderQueue.addOrder(newOrder);

        // Clear form
        customerNameField.setText("");
        flavorComboBox.setSelectedIndex(0);
        sizeComboBox.setSelectedIndex(0);
        toppingsList.clearSelection();

        updateOrderDisplay();
        statusLabel.setText("Order #" + newOrder.getOrderId() + " added successfully! Total: $"
                + String.format("%.2f", newOrder.getTotalPrice()));
    }

    /**
     * MODIFIES: this
     * EFFECTS: prompts user to complete an order
     */
    private void completeOrder() {
        List<Order> pendingOrders = orderQueue.getPendingOrders();
        if (pendingOrders.isEmpty()) {
            statusLabel.setText("No pending orders to complete");
            return;
        }

        String[] orderOptions = new String[pendingOrders.size()];
        for (int i = 0; i < pendingOrders.size(); i++) {
            Order order = pendingOrders.get(i);
            orderOptions[i] = "Order #" + order.getOrderId() + " - " + order.getCustomerName();
        }

        String selectedOption = (String) JOptionPane.showInputDialog(
                this,
                "Select order to complete:",
                "Complete Order", JOptionPane.QUESTION_MESSAGE,
                null, orderOptions, orderOptions[0]);

        if (selectedOption != null) {
            // Extract order ID from selected option
            String orderIdStr = selectedOption.split(" - ")[0].replace("Order #", "");
            int orderId = Integer.parseInt(orderIdStr);

            orderQueue.completeOrder(orderId);
            updateOrderDisplay();
            statusLabel.setText("Order #" + orderId + " marked as completed");
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: toggles between showing all orders and pending orders only
     */
    private void toggleFilter() {
        showOnlyPending = !showOnlyPending;
        updateOrderDisplay();
        statusLabel.setText(showOnlyPending ? "Showing pending orders only" : "Showing all orders");
    }

    /**
     * EFFECTS: saves the order queue to file
     */
    private void saveOrderQueue() {
        try {
            jsonWriter.open();
            jsonWriter.write(orderQueue);
            jsonWriter.close();
            statusLabel.setText("Order queue saved successfully");
            JOptionPane.showMessageDialog(this, "Order queue saved to " + JSON_STORE,
                    "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            statusLabel.setText("Error: Unable to save to file");
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE,
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: loads order queue from file
     */
    private void loadOrderQueue() {
        try {
            orderQueue = jsonReader.read();
            updateOrderDisplay();
            statusLabel.setText("Order queue loaded successfully");
            JOptionPane.showMessageDialog(this,
                    "Loaded " + orderQueue.getPendingOrderCount() + " pending and " 
                            + orderQueue.getCompletedOrderCount() + " completed orders",
                    "Load Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            statusLabel.setText("Error: Unable to load from file");
            JOptionPane.showMessageDialog(this,
                    "Unable to read from file: " + JSON_STORE + "\nStarting with empty queue",
                    "Load Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}