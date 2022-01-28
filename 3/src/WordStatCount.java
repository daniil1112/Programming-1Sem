import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class WordStatCount {
    public static void main(String[] args) {


            String input = args[0];
            String output = args[1];


        try (InputStreamReader file = new InputStreamReader(new FileInputStream(input), "UTF-8");){
            BufferedReader in = new BufferedReader(file);
                char[] buffer = new char[512];
                int wordsCounter = 0;
                String[] words = new String[1];
                int[] wordsCount = new int[1];
                String tmpStr;
                int tmpInt;
                int indexOfCurrentWord;


                StringBuilder currentStr = new StringBuilder();


                while (true){
                    int read = in.read(buffer);

                    if (read == -1){
                        break;
                    }

                    for (int i = 0; i < read; i++){
                        if (Character.isLetter(buffer[i]) || buffer[i] =="'".charAt(0) || Character.DASH_PUNCTUATION == Character.getType(buffer[i])){
                            currentStr.append(Character.toLowerCase(buffer[i]));
                        } else {
                            if (currentStr.length() == 0){
                                continue;
                            }
                            indexOfCurrentWord = check(words, currentStr.toString());
                            if (indexOfCurrentWord == -1){
                                if (wordsCounter == words.length-1){
                                    wordsCount = Arrays.copyOf(wordsCount, wordsCount.length*2);
                                    words = Arrays.copyOf(words, words.length*2);
                                }
                                wordsCount[wordsCounter] = 1;
                                words[wordsCounter] = currentStr.toString();
                                wordsCounter++;
                            } else {
                                wordsCount[indexOfCurrentWord]++;
                            }
                            currentStr = new StringBuilder();
                        }
                    }


                }
                if (currentStr.length() != 0){
                    indexOfCurrentWord = check(words, currentStr.toString());
                    if (indexOfCurrentWord == -1){
                        if (wordsCounter == words.length-1){
                            wordsCount = Arrays.copyOf(wordsCount, wordsCount.length*2);
                            words = Arrays.copyOf(words, words.length*2);
                        }
                        wordsCount[wordsCounter] = 1;
                        words[wordsCounter] = currentStr.toString();
                        wordsCounter++;
                    } else {
                        wordsCount[indexOfCurrentWord]++;
                    }
                }

                int[] wordsIndex = new int[wordsCounter];
                for (int i =0; i < wordsIndex.length; i++){
                    wordsIndex[i] = i;
                }
                //Отсортируем пары по кол-ву вхождений
                for (int i = 0; i<wordsCounter; i++){
                    for (int j = i+1; j<wordsCounter; j++){
                        if (wordsCount[j]<wordsCount[i]){
                            //Свапнем слова
                            tmpStr = words[i];
                            words[i] = words[j];
                            words[j] = tmpStr;
                            //Свапнем счетчик
                            tmpInt = wordsCount[i];
                            wordsCount[i] = wordsCount[j];
                            wordsCount[j] = tmpInt;
                            //Свапнем индексы
                            tmpInt = wordsIndex[i];
                            wordsIndex[i] = wordsIndex[j];
                            wordsIndex[j] = tmpInt;
                        } else if (wordsCount[i] == wordsCount[j] && wordsIndex[i]>wordsIndex[j]){
                            tmpInt = wordsIndex[i];
                            wordsIndex[i] = wordsIndex[j];
                            wordsIndex[j] = tmpInt;
                            //Свапнем слова
                            tmpStr = words[i];
                            words[i] = words[j];
                            words[j] = tmpStr;

                        }
                    }
                }

                try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));){
                    for (int j = 0; j < wordsCounter; j ++){
                        out.write(words[j]+" "+wordsCount[j]+"\n");
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int check(String[] arrayToCheck, String findValue){
        for (int i = 0; i < arrayToCheck.length; i++){
            if (findValue.equals(arrayToCheck[i])){
                return i;
            }
        }
        return -1;
    }

}
