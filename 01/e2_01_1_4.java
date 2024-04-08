import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class e2_01_1_4 {
    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Not enough arguments count");
        }

        String inputFileName = args[0];
        String outputFileName = args[1];
        int ageThreshold = Integer.parseInt(args[2]);

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            List<String> contents = reader.lines().toList();
            for (String content : contents) {

                String[] data = content.split(",");
                try {
                    int age = Integer.parseInt(data[1]);
                    if (age >= ageThreshold) {
                        writer.write(content);
                        writer.newLine();
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred while closing the file: " + e.getMessage());
        }
    }
}