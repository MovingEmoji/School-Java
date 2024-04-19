import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class e2_01_4_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManager manager = new InventoryManager(sc);
        while(true) {
            manager.showCommands();
            if(manager.execute(sc.nextInt()) == false) {
                break;
            }
        }
        sc.close();
    }
}

class Item {

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
    private Scanner sc;

    public InventoryManager(Scanner sc){
        this.category = new HashMap<>();
        this.sc = sc;
    }

    public void showCommands() {
        System.out.println("--------------------------------");
        System.out.println("Inventory Management System");
        System.out.println("--------------------------------");

        System.out.println("1. Add Item");
        System.out.println("2. Display Items by Category");
        System.out.println("3. Remove Item");
        System.out.println("4. Exit");
        System.out.print("Please choose an option: ");
    }

    public boolean execute(int cmd) {
        switch(cmd) {
            case 1:
                addItem();
                return true;

            case 2:
                showItems();
                return true;

            case 3:
                removeItem();
                return true;

            case 4:
                return false;

            default:
                return true;
        }
    }

    public void addItem() {
        System.out.print("Enter category: ");
        String categoryName = sc.next();
        if(!category.containsKey(categoryName)) {
            category.put(categoryName, new ArrayList<>());
        }
        System.out.print("Enter item name: ");
        String itemName = sc.next();
        System.out.print("Enter quantity: ");
        int itemStack = sc.nextInt();
        System.out.print("Enter price per item: ");
        double itemCost = sc.nextDouble();
        Item item = new Item(itemName, itemCost, itemStack);
        if(category.get(categoryName).contains(item)) {
            int index = category.get(categoryName).indexOf(item);
            category.get(categoryName).get(index).setStack(itemStack);
            category.get(categoryName).get(index).setCost(itemCost);
            System.out.println("'" + item.getName() + "' has been updated");
        } else {
            category.get(categoryName).add(item);
            System.out.println("'" + item.getName() + "'has been added to " + categoryName);
        }
    }

    public void removeItem() {
        System.out.print("Enter category: ");
        String categoryName = sc.next();
        if(category.containsKey(categoryName)) {
            System.out.print("Enter item to remove: ");
            int itemId = sc.nextInt() - 1;
            if(category.get(categoryName).get(itemId) != null) {
                Item item = category.get(categoryName).get(itemId);
                category.get(categoryName).remove(item);
                System.out.println("'" + item.getName() + "' has been removed from " + categoryName);
            } else {
                System.out.println("Invaild number");
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
}
/*
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 1
Enter category: Food
Enter item name: apple
Enter quantity: 5
Enter price per item: 100
'apple'has been added to Food
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 1
Enter category: Food  
Enter item name: banana
Enter quantity: 3
Enter price per item: 50
'banana'has been added to Food
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 2
Category: Food
 1. apple(Quantity=5, Price=100.0)
 2. banana(Quantity=3, Price=50.0)
 Total Cost for Food: 650.0
Grand Total Cost: 650.0
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 1
Enter category: Furniture
Enter item name: bed
Enter quantity: 1
Enter price per item: 30000
'bed'has been added to Furniture
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 1
Enter category: Furniture
Enter item name: table
Enter quantity: 2
Enter price per item: 10000
'table'has been added to Furniture
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 2
Category: Furniture
 1. bed(Quantity=1, Price=30000.0)
 2. table(Quantity=2, Price=10000.0)
 Total Cost for Furniture: 50000.0
Category: Food
 1. apple(Quantity=5, Price=100.0)
 2. banana(Quantity=3, Price=50.0)
 Total Cost for Food: 650.0
Grand Total Cost: 50650.0
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 3
Enter category: Food
Enter item to remove: 2
'banana' has been removed from Food
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 2
Category: Furniture
 1. bed(Quantity=1, Price=30000.0)
 2. table(Quantity=2, Price=10000.0)
 Total Cost for Furniture: 50000.0
Category: Food
 1. apple(Quantity=5, Price=100.0)
 Total Cost for Food: 500.0
Grand Total Cost: 50500.0
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items by Category
3. Remove Item
4. Exit
Please choose an option: 4
 */