import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class e2_01_4_2 {
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
class InventoryManager {

    private HashMap<String, ArrayList<String>> category;
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
                System.out.print("Enter category: ");
                addItem(sc.next());
                return true;

            case 2:
                showItems();
                return true;

            case 3:
                System.out.print("Enter category: ");
                removeItem(sc.next());
                return true;

            case 4:
                return false;

            default:
                return false;
        }
    }

    public void addItem(String categoryName) {
        if(!category.containsKey(categoryName)) {
            category.put(categoryName, new ArrayList<>());
        }
        System.out.print("Enter item to add: ");
        String item = sc.next();
        if(!category.get(categoryName).contains(item)) {
            category.get(categoryName).add(item);
            System.out.println("'" + item + "'" + "has been added to " + categoryName);
        }
    }
    public void removeItem(String categoryName) {
        if(category.containsKey(categoryName)) {
            System.out.print("Enter item to remove: ");
            int itemId = sc.nextInt();
            if(category.get(categoryName).get(itemId - 1) != null) {
                String item = category.get(categoryName).get(itemId - 1);
                category.get(categoryName).remove(item);
                System.out.println("'" + item + "'" + "has been removed from " + categoryName);
            }
        }
    }

    public void showItems() {
        for(Map.Entry<String,ArrayList<String>> entry : category.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            for(String item : entry.getValue()) {
                System.out.println((entry.getValue().indexOf(item) + 1) + ". " + item);
            }
        }
    }
}
/*
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 1
Enter item to add: Apple
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 2
Shopping List:
1. Apple
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 1
Enter item to add: Banana
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 2
Shopping List:
1. Apple
2. Banana
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 3
Enter item to remove: 2
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 2
Shopping List:
1. Apple
--------------------------------
Inventory Management System
--------------------------------
1. Add Item
2. Display Items
3. Remove Item
4. Exit
Please choose an option: 4
 */