/*Simon Karasik, lab 2, task 1(1) 
Task: 
Поменять местами строку, содержащую элемент с наибольшим значением в матрице со строкой,
содержащей элемент с наименьшим значением. Вывести на экран получен­ную матрицу. 
Для каждой строки с нулевым элементом на главной диагонали вывести ее  номер 
и значение наибольшего из элементов этой строки.*/
import java.util.Scanner;

public class Problem1 {
    int n;
    int m;
    int matrix[][];

    void readData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n, m");
        n = sc.nextInt();
        m = sc.nextInt();
        matrix = new int[n][m];
        System.out.println("Enter matrix");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i][j] = sc.nextInt();
    }

    static void printMatrix(int[][] mat) {

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
    }

    void solveSubproblem1() {
        int min = matrix[0][0], minLineInd = 0;
        int max = matrix[0][0], maxLineInd = 0;
        for (int i = 0; i < n; i++)
            for( int j = 0; j < m; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxLineInd = i;
                }
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    minLineInd = i;
                }
            }
        int[] line = matrix[minLineInd];
        matrix[minLineInd] = matrix[maxLineInd];
        matrix[maxLineInd] = line;
    }

    void solveSubproblem2() {
        System.out.println("Indices of lines with 0 on diagonal and their maximal elements:");
        boolean was = false;
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0) {
                int max = matrix[i][0];
                was = true;
                for (int j = 0; j < m; j++)
                    max = matrix[i][j] > max ? matrix[i][j] : max;
                System.out.println(i + " " + max);
            }
        }
        if (!was) 
            System.out.println("No lines with 0 on diagonal.");
    }


    public static void main(String[] args) {
        Problem1 solution = new Problem1();
        solution.readData();
        solution.solveSubproblem1();
        System.out.println("Lines with min and max element are swapped:");
        printMatrix(solution.matrix);
        solution.solveSubproblem2();
    }
}
