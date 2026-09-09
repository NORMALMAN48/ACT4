public class FibonacciBase {

    public static void printSequence(int n) {
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            long next = a + b;
            a = b;
            b = next;
        }
    }
}
