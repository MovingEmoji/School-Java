import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class e2_01_1_3 {
    public static void main(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("Not enough arguments count");
        }

        String inputfileName = args[0];
        String outputFileName = args[1];

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputfileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            Object[] lines = reader.lines().toList().toArray();

            for (int i = 0; i < lines.length; i++) {
                writer.write(i + 1 + ":" + lines[i]);
                if (i < lines.length - 1) {
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred while closing the file: " + e.getMessage());
        }
    }
}