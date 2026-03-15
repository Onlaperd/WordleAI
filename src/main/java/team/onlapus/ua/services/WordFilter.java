package team.onlapus.ua.services;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

// ChatGPT Generated, to rewrite.
public class WordFilter {

    public static void main(String[] args) {
        // Define the input and output file paths
        String inputFilePath = "rawset.txt";
        String outputFilePath = "words.txt";

        // Set to store unique words with length 3 to 7
        Set<String> validWords = new HashSet<>();

        // Total size of the file for progress tracking
        long totalBytes = new File(inputFilePath).length();
        long processedBytes = 0;

        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath), StandardCharsets.UTF_8);
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath), StandardCharsets.UTF_8)
        ) {
            String line;
            int lastPct = -1;

            // Process each line in the file
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split by spaces
                for (String word : words) {
                    word = word.trim().replaceAll("[^\\p{L}]", ""); // Clean up and trim

                    // Only add words with 3 to 7 characters (inclusive) and not empty
                    if (word.length() >= 3 && word.length() <= 7) {
                        validWords.add(word);
                    }
                }

                // Track progress
                processedBytes += line.getBytes(StandardCharsets.UTF_8).length;
                printProgress(processedBytes, totalBytes, lastPct);
                lastPct = (int) ((processedBytes * 100) / totalBytes);
            }

            // Writing the result to words.txt in batches
            for (String validWord : validWords) {
                writer.write(validWord);
                writer.newLine();
            }

            System.out.println("\nProcessing complete! Words with 3 to 7 characters have been saved to words.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Prints progress as a percentage and processed bytes count
    private static void printProgress(long processed, long total, int lastPct) {
        int pct = (int) ((processed * 100) / total);
        if (pct != lastPct) {
            System.out.print("\rProgress: " + processed + "/" + total + " bytes (" + pct + "% done)");
        }
    }
}
