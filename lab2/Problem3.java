import java.util.Scanner;

public class Problem3 {
    static void swap(int[] arr, int ind1, int ind2) {
        int t = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = t;
    }

    public static void main(String[] args) {
        int[][] matrix;
        int n, m;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n,m.");
        n = sc.nextInt();
        m = sc.nextInt();
        matrix = new int[n][m];
        System.out.println("Enter matrix");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i][j] = sc.nextInt();

        int[] columns = new int[m];
        for (int i = 0; i < m; i++)
            columns[i] = i;
        for (int i = 0; i < m - 1; i++)
            for (int j = i + 1; j < m; j++)
                if (matrix[0][columns[i]] > matrix[0][columns[j]]) {
                   swap(columns, i, j);
                }
        for (int j = 0; j < m; j++)
            if (columns[j] != j) {
                for (int i = 0; i < n; i++)
                    swap(matrix[i], j, columns[j]);
                swap(columns, j, columns[j]);
            }

        System.out.println("Sort columns by ascending of their first elements:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
