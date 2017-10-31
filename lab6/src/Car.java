import java.util.Comparator;
import java.util.TreeSet;

public class Car {
    private TreeSet<Part> parts;
    private String name;

    private Car() {
        parts = new TreeSet<Part>(
                Comparator.comparing(part -> part.getClass().getName()));
        name = "";
    }

    public Car(Car other) {
        parts = (TreeSet<Part>)other.parts.clone();
        name = other.name;
    }

    public static class Builder {
        private Car car;

        Builder() {
            car = new Car();
        }

        Builder setPart(Part part) {
            car.parts.add(part);
            return this;
        }

        Builder setName(String name) {
            car.name = name;
            return this;
        }

        Car build() {
            return new Car(car);
        }
    }

    public int getPower() {
        int totalPower = 0;
        for (Part part : parts)
            totalPower += part.getPower();
        return totalPower;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + (name.isEmpty() ? "" : " ") + ", power: " + getPower();
    }
}
