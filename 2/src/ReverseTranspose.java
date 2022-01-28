import java.util.Arrays;
import java.util.Scanner;

public class ReverseTranspose {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int[][] resArray = new int[20][20];
        int[] items = new int[20];
        int lines = 0;
        int currentI = 0;
        int x = 0;
        Scanner currentLine;


        while (scanner.hasNextLine()) {
            currentLine = new Scanner(scanner.nextLine());
            currentI = 0;

            while (currentLine.hasNextInt()){
                x = currentLine.nextInt();

                if (resArray.length-1 < currentI ){
                    resArray = Arrays.copyOf(resArray, resArray.length*2);
                    for (int i = (resArray.length/2); i<resArray.length; i++){
                        resArray[i] = new int[20];
                    }
                }

                if (items.length-1 < currentI){
                    items = Arrays.copyOf(items, items.length*2);
                }
                if (resArray[currentI].length-1 < items[currentI]){
                    resArray[currentI] = Arrays.copyOf(resArray[currentI], resArray[currentI].length*2);
                }

                resArray[currentI][items[currentI]] = x;
                items[currentI]++;
                currentI++;
                if (currentI > lines){
                    lines = currentI;
                }
            }

        }
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < items[i]; j++){
                System.out.print(resArray[i][j]+" ");
            }
            System.out.println();
        }
    }


}
