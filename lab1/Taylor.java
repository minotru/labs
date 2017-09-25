public class Taylor {
    static final double EPS = 10E-6;

    public static double exp(double x) {
        double v = 0, a = 1;
        int i = 0;
        while (a > EPS) {
            v += a;
            i++;
            a *= x/i;
        }
        return v;
    }

    public static double oneDivOnePlusXIn3(double x){
        double v = 1, a = 1, current;
        int i = 1;

        do {
            a *= -x;
            current = a * (i + 1) * (i + 2) / 2;
            i++;
            v +=  current;
        } while (Math.abs(current) > EPS);

        return v;
    }

    public static double asin(double x) {
        double v = x, a = x, current;
        int i = 0;

        do {
            i++;
            a *= x * x * (2 * i - 1) / (2 * i) ;
            current = a / (2*i + 1);
            v +=  current;
        } while (Math.abs(current) >= EPS);
        return v;
    }

    public static double lnOnePlusX(double x) {
        double v = 0, a = x, current;
        int i = 1;
        do  {
            current = a / i;
            v += current;
            a *= -x;
            i++;
        } while ((Math.abs(current) > EPS));
        return v;
    }
}
