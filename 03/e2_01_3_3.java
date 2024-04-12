//Naoya Iida
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class e2_01_3_3 {
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java e2_01_3_3 <inputFileName>");
        }

        String inputfileName = args[0];
        HashSet<String> words = new HashSet<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputfileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.toLowerCase().split(" ");
                for(String word : lineWords) {
                    words.add(word);
                }
            }
            for(Object word : words.stream().sorted().toArray()) {
                System.out.println(word);
            }

        } catch(IOException e) {
            System.err.println("An error occurred while closing the file: " + e.getMessage());
        }
    }
}
/*
amazon
apple
banana
brand
com
double
foo
hash
hoge
home
house
int
key
large
orange
scale
secret
slim
slime
 */