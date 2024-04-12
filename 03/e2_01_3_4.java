//Naoya Iida
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class e2_01_3_4 {
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java e2_01_3_4 <inputFileName>");
        }

        String inputfileName = args[0];
        HashMap<String, Integer> hashMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputfileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.toLowerCase().split(" ");
                for(String word : lineWords) {
                    if(hashMap.get(word) == null) {
                        hashMap.put(word,1);
                    } else {
                        hashMap.put(word,hashMap.get(word) + 1);
                    }
                }
            }
            for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch(IOException e) {
            System.err.println("An error occurred while closing the file: " + e.getMessage());
        }
    }
}
/*
banana:1
com:1
large:1
double:1
foo:1
slim:1
scale:1
secret:1
house:1
int:2
home:1
slime:5
orange:1
amazon:2
apple:4
hoge:2
brand:2
key:1
hash:1
 */