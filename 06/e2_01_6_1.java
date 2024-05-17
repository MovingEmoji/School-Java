//Naoya iida
import java.util.ArrayList;
import java.util.Iterator;


class e2_01_6_1 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            int number = iterator.next();
            System.out.println("Number: " + number);
        }
    }   
}
/*
Number: 10
Number: 20
Number: 30
Number: 40
Number: 50
 */