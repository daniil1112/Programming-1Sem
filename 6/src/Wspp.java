import java.io.*;
import java.util.*;

public class Wspp {
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        try (InputStreamReader file = new InputStreamReader(new FileInputStream(input), "UTF-8");) {
            BufferedReader in = new BufferedReader(file);
            ScannerFast scanner = new ScannerFast(in);
            Map<String, List<Integer>> wordsMap = new LinkedHashMap<>();
            Map<String, Integer> wordCount = new HashMap<>();
            String currentWord;
            int wordIndex = 1;
            while (scanner.toNextWord()) {
                currentWord = scanner.getNextWord();
                List<Integer> currentList = wordsMap.get(currentWord);
                if (currentList != null) {
                    currentList.add(wordIndex++);
                } else {
                    wordsMap.put(currentWord, new ArrayList<>(List.of(wordIndex++)));
                }
                wordCount.put(currentWord, wordCount.getOrDefault(currentWord, 0) + 1);
            }

            try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));) {
                for (Map.Entry<String, List<Integer>> entry : wordsMap.entrySet()) {
                    String key = entry.getKey();
                    out.write(key + " " + wordCount.get(key));
                    for (int j : entry.getValue()) {
                        out.write(" " + j);
                    }
                    out.write(System.lineSeparator().length() > 0 ? System.lineSeparator() : "\n");
                }

            }


        } catch (FileNotFoundException e) {
            System.err.println("Input file not exist");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
