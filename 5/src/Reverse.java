import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        int[][] resArray = new int[100][100];
        int[] intCounter = new int[100];
        int lines = 0;
        try {
            ScannerFast test = new ScannerFast(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            while (test.hasNext()){
                if (lines == resArray.length){
                    resArray = Arrays.copyOf(resArray, resArray.length*2);
                    intCounter = Arrays.copyOf(intCounter, intCounter.length*2);
                }
                lines++;
                while (test.testNextInt()){
                    test.toNextInt();
                    int item = test.getNextInt();
                    if (intCounter[lines-1] == 0){
                        resArray[lines-1] = new int[100];
                    }
                    if (intCounter[lines-1] == resArray[lines-1].length-1){
                        resArray[lines-1] = Arrays.copyOf(resArray[lines-1], resArray[lines-1].length*2);
                    }
                    resArray[lines-1][intCounter[lines-1]] = item;
                    intCounter[lines-1]++;
                }
            }
            for (int i = lines-1; i >= 0; i--){
                for (int j = intCounter[i]-1; j >= 0 ; j--){
                    System.out.print(resArray[i][j]+" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
