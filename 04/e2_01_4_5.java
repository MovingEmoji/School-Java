//Naoya Iida
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;


public class e2_01_4_5 {
    public static void main(String[] args) {
        MarketGUI gui = new MarketGUI();
        System.out.println(gui);
    }
}

class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private double cost;
    private int stack;

    public Item(String name, double cost, int stack) {
        this.name = name;
        this.cost = cost;
        this.stack = stack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Item that = (Item) obj;
        return name.equals(that.getName());
    }
}

class InventoryManager {

    private HashMap<String, ArrayList<Item>> category;

    public InventoryManager(){
        this.category = new HashMap<>();
        loadData("shoppingList.dat");
    }

    public void showCommands() {
        System.out.println("--------------------------------");
        System.out.println("Inventory Management System");
        System.out.println("--------------------------------");

        System.out.println("1. Add Item");
        System.out.println("2. Display Items by Category");
        System.out.println("3. Remove Item");
        System.out.println("4. Save and Exit");
        System.out.print("Please choose an option: ");
    }

    public void addItem(String categoryName, Item item) {
        if(!category.containsKey(categoryName)) {
            category.put(categoryName, new ArrayList<>());
        }
        if(category.get(categoryName).contains(item)) {
            int index = category.get(categoryName).indexOf(item);
            category.get(categoryName).get(index).setStack(item.getStack());
            category.get(categoryName).get(index).setCost(item.getCost());
            System.out.println("'" + item.getName() + "' has been updated");
        } else {
            category.get(categoryName).add(item);
            System.out.println("'" + item.getName() + "'has been added to " + categoryName);
        }
    }

    public void removeItem(String categoryName, Item item) {
        if(category.containsKey(categoryName)) {
            if(category.get(categoryName).contains(item)) {
                category.get(categoryName).remove(item);
                System.out.println("'" + item.getName() + "' has been removed from " + categoryName);
            } else {
                System.out.println("Invaild item");
            }
        } else {
            System.out.println("Invaild category");
        }
    }

    public void showItems() {
        double grandCost = 0;
        for(Map.Entry<String,ArrayList<Item>> entry : category.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            double totalCost = 0;
            for(Item item : entry.getValue()) {
                System.out.print(" " + (entry.getValue().indexOf(item) + 1) + ". " + item.getName());
                System.out.println("(Quantity=" + item.getStack() + ", Price=" + item.getCost() + ")");
                totalCost += item.getStack() * item.getCost();
            }
            grandCost += totalCost;
            System.out.println(" Total Cost for " + entry.getKey() + ": " + totalCost);
        }
        System.out.println("Grand Total Cost: " + grandCost);
    }

    public String[] getStringArray() {
        List<String> items = new ArrayList<String>();
        for(Map.Entry<String,ArrayList<Item>> entry : category.entrySet()) {
            for(Item item : entry.getValue()) {
                String value = item.getName() + " (" + entry.getKey() + ") " + "(Quantity=" + item.getStack() + ", Price=" + item.getCost() + ")";
                items.add(value);
            }
        }
        return items.toArray(new String[items.size()]);
    }

    public void saveData() {
        String fileName = "shoppingList.dat";
        try (
            FileOutputStream fileStream = new FileOutputStream(fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)
        ) {
            for(Map.Entry<String,ArrayList<Item>> entry : category.entrySet()) {
                objectStream.writeObject(entry.getKey());
                for(Item item : entry.getValue()) {
                    objectStream.writeObject(item);
                }
            }
            objectStream.writeObject(false);
            objectStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String fileName) {
        File file = new File(fileName);
        if(file.exists()) {
            try (
            FileInputStream fileStream = new FileInputStream(fileName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream)
            ) {
                String categoryName = "";
                while(true) {
                    try {
                        
                        Object obj = objectStream.readObject();
                        if(obj.getClass() == String.class) {
                            categoryName = (String) obj;
                            category.put((String) categoryName, new ArrayList<>());
                        }
                        if(obj.getClass() == Item.class) {
                            category.get(categoryName).add((Item) obj);
                        }
                        if(obj.getClass() == Boolean.class) {
                            break;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class MarketGUI extends JFrame implements ActionListener {

    private InventoryManager manager;
    private JPanel panel;

    private JList<String> items;

    private JTextField category;
    private JTextField name;
    private JTextField stack;
    private JTextField cost;

    private JButton addButton;
    private JButton removeButton;
    private JButton exitButton;

    public MarketGUI() {
        manager = new InventoryManager();
        this.setTitle("MarketManager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 320);
        category = new JTextField(20);
        name = new JTextField(20);
        stack = new JTextField(5);
        cost = new JTextField(5);
        addButton = new JButton("Add New Item");
        removeButton = new JButton("Remove Item");
        exitButton = new JButton("Exit");
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        exitButton.addActionListener(this);
        showPanel();
    }

    public void showPanel() {
        if(panel != null) {
            this.remove(panel);
        }
        panel = new JPanel();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        panel.add(new JLabel("Select and press button to remove"));
        items = new JList<>(manager.getStringArray());
        JScrollPane scrollpane = new JScrollPane(items);
        scrollpane.setPreferredSize(new Dimension(300, 100));
        panel.add(scrollpane);

        JPanel textBoxes = new JPanel();
        textBoxes.setLayout(new BoxLayout(textBoxes, BoxLayout.PAGE_AXIS));
        JPanel line = new JPanel();
        line.add(new JLabel("Category "));
        line.add(category);
        textBoxes.add(line);
        line = new JPanel();
        line.add(new JLabel("Name       "));
        line.add(name);
        textBoxes.add(line);
        line = new JPanel();
        line.add(new JLabel("Cost "));
        line.add(cost);
        textBoxes.add(line);
        line.add(new JLabel("Stack "));
        line.add(stack);
        panel.add(textBoxes);

        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(removeButton);
        panel.add(buttons);
        panel.add(exitButton,BorderLayout.PAGE_END);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            if(!category.getText().equals("") && !name.getText().equals("") && !cost.getText().equals("") && !stack.getText().equals("")) {
                manager.addItem(category.getText(), new Item(name.getText(), Double.parseDouble(cost.getText()), Integer.parseInt(stack.getText())));
            } else {
                JOptionPane.showMessageDialog(this, new JLabel("Please enter all values"));
            }
        } else if(e.getSource() == removeButton) {
            if(items.getSelectedValue() != null) {
                String[] data = items.getSelectedValue().split(" ");
                String itemName = data[0];
                String categoryName = data[1].replace("(", "").replace(")", "");
                manager.removeItem(categoryName, new Item(itemName, 0, 0));
            }
        } else if(e.getSource() == exitButton) {
            manager.saveData();
            System.exit(0);
        }
        showPanel();
    }
}