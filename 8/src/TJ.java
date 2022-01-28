import java.util.Scanner;

public class TJ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            int j = 0;
            String str = scanner.nextLine();
            for (char ch : str.toCharArray()) {
                matrix[i][j++] = Integer.parseInt(String.valueOf(ch));
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; i++) {
                if (matrix[i][j] == 1) {
                    for (int k = j + 1; k < n; k++) {
                        matrix[i][k] = matrix[i][k];
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
