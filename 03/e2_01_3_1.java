//NaoyaIida
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class e2_01_3_1 {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }
        measureListPerf(linkedList);
        measureListPerf(arrayList);
    }

    public static void measureListPerf(List<Integer> list) {
        int size = list.size();
        int lastIndex = size - 1;
        long startTime = System.nanoTime();
        int lastElement = list.get(lastIndex);
        long endTime = System.nanoTime();
        System.out.println(list.getClass() + " [Time] " + (endTime - startTime) + " ns");
    }
    
}
/*
class java.util.LinkedList [Time] 9700 ns
class java.util.ArrayList [Time] 1100 ns
 */