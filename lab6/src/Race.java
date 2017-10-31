import java.util.Random;

public class Race {
    private static int move(Car car) {
        return (int)(car.getPower() * (Math.random() * 0.5 + 1));
    }

    static private void  onTurn(final Car car, int onDistance) {
        System.out.println(car.getName() + " has moved on " + onDistance + "!");
    }

    static Car race(Car car1, Car car2, double trackLength) {
        Car cars[] = {car1, car2};
        int d[] = {0, 0};
        int turnOf = new Random().nextInt(2);
        do {
            turnOf = 1 - turnOf;
            int delta = move(cars[turnOf]);
            onTurn(cars[turnOf], delta);
            d[turnOf] += delta;
        } while (d[turnOf] < trackLength);
        System.out.println(cars[turnOf].getName() + " won!");
        return cars[turnOf];
    }
}
