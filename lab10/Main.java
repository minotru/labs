import java.awt.*;
import java.applet.Applet;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/*
<applet code = "Main" width = 300 height = 300>
    <param name = "backgroundColor" value = "120 30 30">
    <param name = "segmentStart" value = "50 100">
    <param name = "segmentEnd" value = "250 100">
    <param name = "segmentColor" value = "0 255 0">
</applet>
*/

public class Main extends Applet implements Runnable {
    static final int FPS = 60;
    static final double POINT_SPEED = 0.01;
    static final double ROTATE_SPEED = Math.toRadians(2);
    Thread thread;
    Point segmentStart, segmentEnd;
    Color backgroundColor, segmentColor;
    boolean stopFlag;
    Point point;
    double angle;
    double rotatingSegmentLength;
    int pointDirection;
    double pointProportion;

    private int[] parseArray(String s) {
        String[] tokens = s.split(" ");
        int[] arr = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++)
            try {    
                arr[i] = Integer.parseInt(tokens[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        return arr;
    }

    private Color parseColor(String s) {
        int[] arr = parseArray(s);
        return new Color(arr[0], arr[1], arr[2]);
    }

    private Point parsePoint(String s) {
        int[] arr = parseArray(s);
        return new Point(arr[0], arr[1]);
    }

    public double length(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));
    }

    public void init() {
        segmentStart = parsePoint(getParameter("segmentStart"));
        segmentEnd = parsePoint(getParameter("segmentEnd"));
        backgroundColor = parseColor(getParameter("backgroundColor"));
        segmentColor = parseColor(getParameter("segmentColor"));
        point = new Point((segmentStart.x + segmentEnd.x) / 2, (segmentStart.y + segmentEnd.y) / 2);
        rotatingSegmentLength = (Math.random() * 100) + 20;
        angle = Math.random() * Math.PI * 2;
        pointDirection = 1;
        pointProportion = 0.5;
        //segmentLength = length(segmentStart, segmentEnd);
        //segmentAngle = Math.atan((segmentEnd.y - segmentStart.y) / (segmentEnd.x - segmentStart.x));
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    private void movePoint() {
        //point.x += (int) (Math.ceil(POINT_SPEED * Math.cos(segmentAngle))) * pointDirection;
        // point.y += (int) (Math.ceil(POINT_SPEED * Math.sin(segmentAngle))) * pointDirection;
        // if (point.x < segmentStart.x || point.x > segmentEnd.x ||
        //     point.y < segmentStart.y || point.y > segmentEnd.y)
        //     pointDirection *= -1;
        pointProportion += POINT_SPEED * pointDirection;
        if (pointProportion < 0 || pointProportion > 1)
            pointDirection *= -1;
        point.x = segmentStart.x + (int)Math.ceil((segmentEnd.x - segmentStart.x) * pointProportion);
        point.y = segmentStart.y + (int)Math.ceil((segmentEnd.y - segmentStart.y) * pointProportion);

    }

    public void run() {
      
        stopFlag = false;

        while (!stopFlag) {
            try {
                repaint();
                Thread.sleep(1000 / FPS);
                angle += ROTATE_SPEED;
                movePoint();
                if (stopFlag)
                    break; 
            } catch (InterruptedException e) { 

            }
        }
    }

    public void stop() {
        stopFlag = true;
    }



    public void paint(Graphics g) {
        setBackground(backgroundColor);
        g.setColor(segmentColor);
        g.drawLine(segmentStart.x, segmentStart.y, segmentEnd.x, segmentEnd.y);
        int x1, y1;
        x1 = point.x + (int)Math.round(rotatingSegmentLength * Math.cos(angle));
        y1 = point.y + (int)Math.round(rotatingSegmentLength * Math.sin(angle));
        g.drawLine(point.x, point.y, x1, y1);
    }
}