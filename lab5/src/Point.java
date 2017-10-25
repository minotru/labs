public class Point {
    private double x;
    private double y;

    Point(Point p) {
        this(p.x, p.y);
    }

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point() {
        this(0, 0);
    }

    void setX(double x) {
        this.x = x;
    }

    double getX() {
        return this.x;
    }

    void setY(double y) {
        this.y = y;
    }
    double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}