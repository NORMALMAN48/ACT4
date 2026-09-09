import java.util.Scanner;

public class Menu { // maneja menu entero
    public static void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("----------------- Menu -----------------");
        System.out.println("1. Fibonacci sequence");
        System.out.println("2. Subset sum finder");
        System.out.println("3. Sudoku solver (backtracking)");
        System.out.println("0. Exit");

        int choice = readInt(sc, "Choose: ");

        if (choice == 1) {
            runFibonacci(sc); //inicia y da el scanner
        } else if (choice == 2) {
            runSubsetSum(sc);
        } else if (choice == 3) {
            runSudoku();
        } else {
            System.out.println("Closing ...");
        }

        sc.close();
    }

    // pregunta index en serie
    private static void runFibonacci(Scanner sc) {
        int n = readInt(sc, "Enter index: ");
        n++;
        Fibonacci.printSequence(n);
    }


    // pide numeros, luego una meta,  y resuelve
    private static void runSubsetSum(Scanner sc) {
        int[] numbers = new int[100]; // maxima cantidad
        int count = 0;

        System.out.println("Enter numbers one at a time. Type 'done' to stop.");
        while (true) {
            System.out.print("Number (or 'done'): ");
            String input = sc.next().trim(); //corta espacios vacios

            if (input.equalsIgnoreCase("done")) {
                break; // si termina
            }

            if (count >= numbers.length) {
                System.out.println("Max nums reached(100).");
                continue;
            }

            try {
                int value = Integer.parseInt(input); //intenta ; sin crash
                if (value <= 0) { // si negativo o 0
                    System.out.println("Only positive numbers accepted.");
                    continue;
                }
                numbers[count] = Integer.parseInt(input); // intenta meter como int
                count++; //suma al max cantidad
            } catch (NumberFormatException e) { //si no es un int
                System.out.println("Invalid Int.");
            }
        }

        int target; // meta de suma
        while (true) {
            target = readInt(sc, "Enter target sum: ");
            if (target == 0) {
                System.out.println("0 is not a valid target, try again.");

            }if (target < 0) { //mayor a 0
                System.out.println("Target must be a positive number.");

            } else {
                break;
            }
        }
        printNumbers(numbers,count); // muestra array
        SubsetSum.solve(numbers, count, target); // inicia funcion; array de input, length de este, meta por sumar.
    }

    private static void printNumbers(int[] numbers, int count) {
        StringBuilder sb = new StringBuilder(); //sb para construir texto
        for (int i = 0; i < count; i++) {
            sb.append(numbers[i]); //agrega char por elemento
            if (i < count - 1) {
                sb.append(", "); // si no es el ultimo
            }
        }
        System.out.println("Numbers: [" + sb + "]");
    }


    // resuelve sudoku
    private static void runSudoku() {
        // cambiando esto
        String filePath = "C:\\Users\\sudoku.txt";
        SudokuSolver.solveFromFile(filePath);
    }




    private static int readInt(Scanner sc, String prompt) {
        while (true) { // preg hasta tener int
            System.out.print(prompt);
            String token = sc.next().trim();// sc.next lee sin crashear
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {    // integer.parseint checa si crashearia usar
                System.out.println("'" + token + "' is not a valid int.");
            }
        }
    }
}

