import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void printRects(List<Rectangle> rects) {
        for (Rectangle rect : rects) {
            System.out.print(rect.toString() + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            while (reader.ready()) {
                rects.add(new Rectangle(reader.readLine()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printRects(rects);

        System.out.println("Sorted by square:");
        Collections.sort(rects);
        printRects(rects);

        Rectangle.setCompareByPerimeter();
        System.out.println("Sorted by perimeter");
        Collections.sort(rects);
        printRects(rects);

        System.out.println("Iterating over fields:");
        for (Rectangle rect : rects) {
            Iterator<Point> it = rect.iterator();
            while (it.hasNext()) {
                System.out.print(it.next());
            }
            System.out.println();
        }
    }
}