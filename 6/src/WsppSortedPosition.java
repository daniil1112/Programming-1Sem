import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WsppSortedPosition {
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        try (InputStreamReader file = new InputStreamReader(new FileInputStream(input), "UTF-8");) {
            BufferedReader in = new BufferedReader(file);
            ScannerFast scanner = new ScannerFast(in);
            Map<String, LinkedList<Integer>> wordsMap = new TreeMap<>();
            LinkedList<Integer> currentList;
            String currentWord;
            while (scanner.toNextWord()) {
                currentWord = scanner.getNextWord();
                currentList = wordsMap.get(currentWord);
                if (currentList != null) {
                    currentList.add(scanner.getLineIndex());
                    currentList.add(scanner.getItemIndex());
                    currentList.set(0, currentList.get(0) + 1);
                } else {
                    wordsMap.put(currentWord, new LinkedList<>(List.of(1, scanner.getLineIndex(), scanner.getItemIndex())));
                }
            }

            try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));) {
                for (Map.Entry<String, LinkedList<Integer>> entry : wordsMap.entrySet()) {
                    out.write(entry.getKey());
                    int entryValCounter = 0;
                    for (int j : entry.getValue()) {
                        if (entryValCounter == 0) {
                            out.write(" " + j);
                        } else if (entryValCounter % 2 == 1) {
                            out.write(" " + j + ":");
                        } else {
                            out.write(Integer.toString(j));
                        }
                        entryValCounter++;
                    }
                    out.write(System.lineSeparator().length() > 0 ? System.lineSeparator() : "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("File input reader");
        }
    }
}
