import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolver {

    private static final int SIZE = 9; // atributo final static; no cambia nunca el tama;o

    // lee sudoku y trata resolver
    public static void solveFromFile(String filePath) {
        int[][] board = readBoard(filePath); // lee 9x9 string y traduce a un array de ints
        if (board == null) {
            return; // si esta vacio
        }

        System.out.println("Puzzle loaded:");
        System.out.println("--------------");
        printBoard(board);

        if (solve(board)) { // si la recursion funciona aqui; y findEmptyCell regresa null.
            System.out.println("Solved puzzle:");
            System.out.println("--------------");
            printBoard(board);
        } else { // si despues de todas las recursiones y caminos posibles regresa falso.
            //
            System.out.println("This puzzle has no solution.");
        }
    }

    // lee el archivo y convierte en array
    private static int[][] readBoard(String filePath) {
        int[][] board = new int[SIZE][SIZE]; // variable eternamente 9
        // inicia con 0s
        // try por si el pathing no funciona
        // BufferedReader para leer el txt
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int row = 0; // va una por una; contador
            String line;

            while ((line = reader.readLine()) != null) { // si la linea no esta vacia
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // brincar si vacio
                }

                if (row >= SIZE) { //si mas del max
                    System.out.println("Error: file has more than " + SIZE + " rows.");
                    return null;
                }
                if (line.length() != SIZE) { //si es menos o mas del numero
                    System.out.println("Error: row " + (row + 1) + " must have exactly " + SIZE + " characters.");
                    return null;
                }
                // asumiendo 9x9 si esta presente y lo previo es falso:
                for (int col = 0; col < SIZE; col++) {
                    char ch = line.charAt(col); // leer char por char; usando index col
                    if (ch < '0' || ch > '9') { // si no esta entre rango (0,9)
                        System.out.println("Error: in [" + (row + 1) + "][" + (col + 1) + "]");
                        return null;
                    }
                    board[row][col] = ch - '0'; // si pasa todos los filtros, agrega.
                    // traduce ASCII , so un 0 seria 0 relmente, y un 5(53 ascii) se traduce a 5.
                }
                row++; //suma en fila
            }

            if (row != SIZE) { // si contador supera maimo de filas
                System.out.println("Error. Rows needed: " + SIZE + ", rows found: " + row);
                return null;
            }
        } catch (IOException e) { // si algun error crashearia
            System.out.println("Error: could not read file '" + filePath + "'.");
            return null;
        }

        return board; // tras leer, regresa
    }

    // intenta resolver con backtracking
    private static boolean solve(int[][] board) {
        int[] emptyCell = findEmptyCell(board); // busca celda vacia dentro de array board
        if (emptyCell == null) {
            return true; // si no hay vacios; resuelve.
        }

        int row = emptyCell[0]; // porque findEmptyCell regresa un array de 2 elementos
        int col = emptyCell[1]; // segunda posicion en array dado por emptyCell

        for (int digit = 1; digit <= SIZE; digit++) { // intenta de digitos 1-9 (igual a SIZE)
            if (isValid(board, row, col, digit)) { // verifica si reglas sudoku son verdad ( linease y 3x3 caja).
                board[row][col] = digit; // si se puede poner; pone un digito
                if (solve(board)) { // intenta continuar resolviendo en esta rama, pero si falla, regresaria solve falso.
                    return true; // si eventualmente llega a emptyCell === null
                }
                board[row][col] = 0; // si no funciona, pone un 0 para limpiar previo error, e intenta con siguiente.
            }
        }

        return false; // si ningun digito funciono  // aqui regresa en nivel de recursividad
    }

    // regresa coordinadas de celda vacia
    private static int[] findEmptyCell(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) { // si esta vacio
                    return new int[] { row, col }; // crea un array de 2 elementos, row  y col
                }
            }
        }
        return null; // si no hay celdas vacias ('0')
    }

    // checa que poner un digito no rompe reglas
    private static boolean isValid(int[][] board, int row, int col, int digit) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == digit) {
                return false;
            }
            if (board[i][col] == digit) {
                return false;
            }
        }

        int boxRowStart = (row / 3) * 3; // se usa '/' porque va de 0,1,2; y mutiplica esto para tener 000333999
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) { // solo necesita volver al 0
            for (int c = boxColStart; c < boxColStart + 3; c++) { // necesita volver al 0
                if (board[r][c] == digit) { // ya con 0,0 ; cicla hasta 9,9 en caja. (3,3)
                    return false; // conflicto de 3x3
                }
            }
        }

        return true;
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < SIZE; col++) {
                sb.append(board[row][col]);
            }
            System.out.println(sb.toString());
        }
    }
}
