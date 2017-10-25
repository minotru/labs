import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Rectangle extends Figure implements Comparable<Rectangle>, Iterable<Point> {
    private Point topLeft;
    private Point bottomRight;

    private static enum CompareBy {
        BY_SQUARE, BY_PERIMETER
    }

    private static CompareBy compareBy = CompareBy.BY_SQUARE;

    public Rectangle(String s) throws Exception{
        Scanner sc = new Scanner(s);
        ArrayList<Double> args = new ArrayList<Double>();
        while (sc.hasNextDouble())
            args.add(sc.nextDouble());
        if (args.size() != 4)
            throw new Exception("Invalid format");
        topLeft = new Point(args.get(0), args.get(1));
        bottomRight = new Point(args.get(2), args.get(3));
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = new Point(topLeft);
        this.bottomRight = new Point(bottomRight);
    }

    public Rectangle() {
        this(new Point(), new Point());
    }

    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private int ind = 0;
            @Override
            public boolean hasNext() {
                return ind < 2;
            }

            @Override
            public Point next() {
                if (ind == 0) {
                    ind++;
                    return topLeft;
                }
                else {
                    ind++;
                    return bottomRight;
                }
            }
        };
    }

    public static void setCompareByPerimeter() {
        compareBy = CompareBy.BY_PERIMETER;
    }

    public static void setCompareBySquare() {
        compareBy = CompareBy.BY_SQUARE;
    }

    public int compareTo(Rectangle other) {
        if (this.compareBy == CompareBy.BY_SQUARE)
            return Double.compare(this.getSquare(), other.getSquare());
        else
            return Double.compare(this.getPerimeter(), other.getPerimeter());
    }

    public double getSquare() {
        return Math.abs(bottomRight.getX() - topLeft.getX()) * Math.abs(topLeft.getY() - bottomRight.getY());
    }

    public double getPerimeter() {
        return (Math.abs((bottomRight.getX() - topLeft.getX())) + Math.abs((topLeft.getY() - bottomRight.getY()))) * 2;
    }

    @Override
    public String toString() {
        return topLeft.toString() + ","+ bottomRight.toString();
    }

}