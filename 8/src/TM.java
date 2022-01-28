import java.util.HashMap;
import java.util.Scanner;

public class TM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        int[] a = new int[2000];
        for (int m = 0; m < s; m++) {
            int n = scanner.nextInt();
            for (int k = 0; k < n; k++) {
                a[k] = scanner.nextInt();
            }

            int res = 0;
            HashMap<Integer, Integer> c = new HashMap<>();
            for (int j = n - 1; j >= 1; j--) {
                if (j < n - 1) {
                    c.put(a[j + 1], c.getOrDefault(a[j + 1], 0) + 1);
                }
                for (int i = 0; i <= j - 1; i++) {
                    res += c.getOrDefault(2 * a[j] - a[i], 0);
                }
            }
            System.out.println(res);
        }
        scanner.close();
    }
}
