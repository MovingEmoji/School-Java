import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class e2_01_1_2 {
    public static void main(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("Not enough arguments count");
        }

        String fileName = args[0];
        String resultFileName = args[1];
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileName));

            BufferedWriter writer = new BufferedWriter(new FileWriter(resultFileName));

            Object[] lines = reader.lines().toList().toArray();

            for (int i = 0; i < lines.length; i++) {
                writer.write(i + 1 + ":" + lines[i]);
                if (i < lines.length - 1) {
                    writer.newLine();
                }
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing the file: " + e.getMessage());
                }
            }
        }
    }
}