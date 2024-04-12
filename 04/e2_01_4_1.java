import java.util.ArrayList;
import java.util.Scanner;

public class e2_01_4_1 {
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

    private ArrayList<String> items;
    private Scanner sc;

    public InventoryManager(Scanner sc){
        this.items = new ArrayList<>();
        this.sc = sc;
    }

    public void showCommands() {
        System.out.println("--------------------------------");
        System.out.println("Inventory Management System");
        System.out.println("--------------------------------");

        System.out.println("1. Add Item");
        System.out.println("2. Display Items");
        System.out.println("3. Remove Item");
        System.out.println("4. Exit");
        System.out.print("Please choose an option: ");
    }

    public boolean execute(int cmd) {

        switch(cmd) {
            case 1:
                System.out.print("Enter item to add: ");
                addItem(sc.next());
                return true;

            case 2:
                showItems();
                return true;

            case 3:
                System.out.print("Enter item to remove: ");
                removeItem(sc.nextInt());
                return true;

            case 4:
                return false;

            default:
                return false;
        }
    }

    public void addItem(String itemName) {
        if(items.contains(itemName) == false) {
            items.add(itemName);
        }
    }

    public void removeItem(int itemId) {
        items.remove(items.get(itemId - 1));
    }

    public void showItems() {
        System.out.println("Shopping List:");
        for(String item : items) {
            System.out.println((items.indexOf(item) + 1) + ". " + item);
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