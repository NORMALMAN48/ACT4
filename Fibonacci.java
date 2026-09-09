public class Fibonacci {

    public static void printSequence(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number.");
            return;
        }

        System.out.print("Fibonacci sequence: ");
        for (int i = 0; i < n; i++) {
            System.out.print(fib(i) + " ");
        }
        System.out.println();
    }

    private static long fib(int n) {
        if (n == 0) {
            return 0; // base case
        }
        if (n == 1) {
            return 1; // base case
        }
        return fib(n - 1) + fib(n - 2); // recursive case
    }
}
