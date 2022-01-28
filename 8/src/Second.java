import java.util.Scanner;

public class Second {
    public static void main(String[] args) {
        int minDiv = 710;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = -25000; i < -25000 + n; i++) {
            System.out.println(minDiv * i);
        }

    }
}
