import java.util.Scanner;

public class TI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int h = scanner.nextInt();
            xMin = Math.min(xMin, x - h);
            xMax = Math.max(xMax, x + h);
            yMin = Math.min(yMin, y - h);
            yMax = Math.max(yMax, y + h);
        }
        long xRes = (xMin + xMax) / 2;
        long yRes = (yMin + yMax) / 2;
        long hRes = (Math.max(xMax - xMin, yMax - yMin) + 1) / 2;
        System.out.println(xRes + " " + yRes + " " + hRes);
    }
}
