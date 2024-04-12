//Naoya Iida
public class e2_01_2_1 {
    public static void main(String[] args) {
        CustomObject obj1 = new CustomObject("John", 1);
        CustomObject obj2 = new CustomObject("Alice", 2);
        CustomObject obj3 = new CustomObject("John", 1);
        CustomObject obj4 = obj2;
        System.out.println(obj1 == obj2); //A
        System.out.println(obj1 == obj3); //B
        System.out.println(obj2 == obj4); //C
        System.out.println(obj1.equals(obj2)); //D
        System.out.println(obj1.equals(obj3)); //E
        System.out.println(obj2.equals(obj4)); //F

    }
}

class CustomObject {
    private String name;
    private int id;

    public CustomObject(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomObject that = (CustomObject) obj;
        return id == that.id && name.equals(that.name);
    }

}
/*
false
false
true
false
true
true
 */

