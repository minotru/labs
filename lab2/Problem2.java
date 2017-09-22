import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Problem2 {

    public static void main(String[] args) {
        int[] matrix;
        int n, m;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n,m.");
        n = sc.nextInt();
        m = sc.nextInt();
        matrix = new int[n * m];
        System.out.println("Enter matrix");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i * n + j] = sc.nextInt();

        Arrays.sort(matrix);
        int value = matrix[matrix.length - 1], cnt = 0;
        for (int i = matrix.length - 1; i > 0; i--) {
            if (matrix[i] == value)
                cnt++;
            else if (cnt == 2 && matrix[i] != value)
                break;
            else {
                value = matrix[i];
                cnt = 1;
            }
        }

        if (cnt == 2)
            System.out.println("Maximum value that appears 2 times is " + value);
        else
            System.out.println("No value in matrix that appears 2 times.");
    }
}
