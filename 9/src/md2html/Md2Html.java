package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class Md2Html {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        StringBuilder currentStr = new StringBuilder();
        StringBuilder res = new StringBuilder();
        try (InputStreamReader file = new InputStreamReader(new FileInputStream(inputFileName), "UTF-8");){
            BufferedReader reader = new BufferedReader(file);
            String currentLine = reader.readLine();
            while (currentLine!=null){
                if (currentLine.length()==0){
                    if (currentStr.length()>0){
                        LineElement tmp = new LineElement();
                        if (res.length() > 0){
                            res.append(System.lineSeparator());
                        }
                        res.append(tmp.getElement(currentStr).toHTML());
                        currentStr = new StringBuilder();
                    }
                } else {
                    if (currentStr.length() > 0){
                        currentStr.append(System.lineSeparator());
                    }
                    for (int i = 0; i<currentLine.length(); i++){
                        currentStr.append(currentLine.charAt(i));
                    }

                }
                currentLine = reader.readLine();
            }
            if (currentStr.length()>0){
                if (res.length() > 0){
                    res.append(System.lineSeparator());
                }
                LineElement tmp = new LineElement();
                res.append(tmp.getElement(currentStr).toHTML());
            }
            try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));){
                out.write(res.toString());
            } catch (IOException e){
                System.out.println("Error use out file");
            }
        } catch (IOException e) {
            System.err.println("Error use input file");
        }

    }
}
