public class SubsetSum {

    private static int[] chosenIndices; // indices incluido en intento
    private static int chosenCount;

    // intenta agregar nums para alcanzar target
    public static void solve(int[] numbers, int count, int target) {
        chosenIndices = new int[count]; // array de indices elegidos para sumar (backtracking)
        chosenCount = 0; // length de chosenindices

        if (findSubset(numbers, count, target, 0)) { // si no conflictua ( que no sobrepase el objetivo, o no se acaben nums )
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chosenCount; i++) { //suma a length de chosencount
                sb.append(numbers[chosenIndices[i]]); //agrega  elemento de numbers con indices elegidos
                if (i < chosenCount - 1) {
                    sb.append(" + "); // si penultimo
                }
            }
            System.out.println("Subset found: " + sb.toString() + " = " + target); // si da true
        } else {
            System.out.println("No subset of the given numbers adds up to " + target + "."); // si recursividad dio false
        }
    }

    // backtracing recursivo
    private static boolean findSubset(int[] numbers, int count, int remaining, int index) {
        if (remaining == 0) {
            return true;
        }
        if (index >= count) {
            return false;
        }

        // intenta incluir nueva adicion (le resta a remaining, meta).
        chosenIndices[chosenCount] = index;
        chosenCount++;
        if (findSubset(numbers, count, remaining - numbers[index], index + 1)) {
            return true;
        }
        chosenCount--; // si esa rama dio falso; elimina ultima adicion , resta cantidad de indices agregados

        // intenta denuevo, excluyendo ese numero (numbers[index])
        return findSubset(numbers, count, remaining, index + 1);
    }
}
