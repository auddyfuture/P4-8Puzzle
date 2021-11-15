import stdlib.StdOut;

public class Ramanujan1 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int a = 1; a * a * a <= n; a++) {
            for (int b = a + 1; b * b * b <= n - (a * a * a); b++) {
                for (int c = a + 1; c * c * c <= n; c++) {
                    for (int d = c + 1; d * d * d <= (n - c * c * c); d++) {
                        if (a * a * a + b * b * b == c * c * c + d * d * d) {
                            StdOut.println((a * a * a + b * b * b) + " = " + a + "^3 + " +
                                    b + "^3 " +
                                    "= " + c + "^3 + " + d + "^3");
                        }
                    }
                }
            }
        }
    }
}
