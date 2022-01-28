import java.util.Arrays;
import java.util.Scanner;

public class Reverse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] lineCounter = new int[20];
        int[][] inputMatrix = new int[20][0];
        int lines = 0;
        while (scanner.hasNextLine()){
            int[] currentItems = new int[20];
            Scanner lineScanner = new Scanner(scanner.nextLine());
            int counter = 0;
            while (lineScanner.hasNextInt()) {
                if (counter >= currentItems.length) {
                    currentItems = Arrays.copyOf(currentItems, currentItems.length * 2);
                }
                currentItems[counter++] = lineScanner.nextInt();
            }
            if (lines >= inputMatrix.length){
                inputMatrix = Arrays.copyOf(inputMatrix, inputMatrix.length*2);
                lineCounter = Arrays.copyOf(lineCounter, lineCounter.length*2);
            }
            lineCounter[lines] = counter;
            if (counter > 0){
                inputMatrix[lines++] = currentItems;
            } else {
                inputMatrix[lines++] = null;
            }

        }
        for (int i = lines-1; i >= 0; i--){
            for (int j = lineCounter[i]-1; j>=0 ; j--){
                System.out.print(inputMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
}
