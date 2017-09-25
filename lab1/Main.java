import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double x;
        System.out.println("Enter x.");
        x = sc.nextDouble();
        System.out.println("exp(x) = " + Taylor.exp(x));
        System.out.println("Math.exp(x) = " + Math.exp(x));
        System.out.println("1/(1 + x)^3 = " +  Taylor.oneDivOnePlusXIn3(x));
        System.out.println("Math 1/(1 + x)^3 = " + Math.pow(1+x, -3));
        System.out.println("asin(x) = " +  Taylor.asin(x));
        System.out.println("Math asin(x) = " + Math.asin(x));
        System.out.println("ln(1 + x) = " +  Taylor.lnOnePlusX(x));
        System.out.println("Math ln(1+x) = " + Math.log(1 + x));
    }
}