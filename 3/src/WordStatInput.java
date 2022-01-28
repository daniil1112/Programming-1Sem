import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    public static void main(String[] args) {

            String input = args[0];
            String output = args[1];


            try (InputStreamReader file = new InputStreamReader(new FileInputStream(input), "UTF-8");){
                char[] buffer = new char[512];
                BufferedReader in = new BufferedReader(file);
                StringBuilder currentStr = new StringBuilder();
                LinkedHashMap<String, Integer> kv = new LinkedHashMap<>();

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
                            if (kv.get(currentStr.toString()) == null){
                                kv.put(currentStr.toString(), 1);
                            } else {
                                kv.put(currentStr.toString(), kv.get(currentStr.toString())+1);
                            }
                            currentStr = new StringBuilder();
                        }
                    }


                }
                if (currentStr.length() != 0){
                    if (kv.get(currentStr.toString()) == null){
                        kv.put(currentStr.toString(), 1);
                    } else {
                        kv.put(currentStr.toString(), kv.get(currentStr.toString())+1);
                    }
                }
//                Writer out = new FileWriter(output);
                try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));){
                    for (Map.Entry<String, Integer> entry: kv.entrySet()){
                        out.write(entry.getKey()+" "+entry.getValue()+"\n");
                    }
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
