//Simon Karasik, course 2, group 5
//lab 6, variant 1(races + Builder pattern)
import java.util.List;

public class Main {
    private static void fillShopRandomly(Shop shop) {
        int cnt = (int)(Math.random() * 100) + 6;
        for (int i = 0; i < cnt; i++){
            int type = (int) (Math.random() * 3);
            Part part;
            int power = (int) (Math.random() * 100);
            int price = (int) (Math.random() * 1000);
            int quantity = (int) (Math.random() * 12);
            if (type == 0)
                part = new Engine(power, price);
            else if (type == 1)
                part = new Transmission(power, price);
            else
                part = new Tyres(power, price);
            shop.addGood(part, quantity);
        }

    }

    public static void main(String[] args) {
        Shop shop= new Shop();
        fillShopRandomly(shop);

        Car[] cars = new Car[2];
        for (int i = 0; i < 2; i++) {
            Car.Builder carBuilder = new Car.Builder();
            String carName;
            if (i == 0)
                carName = "Lada Kalina";
            else
                carName = "Porsche 911";
            carBuilder.setName(carName);
            int budget = (int) (Math.random() * 1000);
            System.out.println(carName + " equips...");
            System.out.println("It's budget is " + budget);
            List<Part> parts = shop.buyRandomSet(budget);
            System.out.println("And that will drive it:");
            for (Part part : parts) {
                System.out.println(part);
                carBuilder.setPart(part);
            }
            cars[i] = carBuilder.build();
            System.out.println(carName + " is ready to go, yeah!\n");
        }
        int trackLength = (int)(Math.random() * 1000);
        for (int i = 0; i < 2; i++)
            System.out.println(cars[i].getName() + " is on the track...");
        System.out.println("Track's length is " + trackLength);
        System.out.println("Ready...");
        System.out.println("Steady...");
        System.out.println("Go!!!");
        Race.race(cars[0], cars[1], trackLength);
        System.out.println("\nRace is over, thanks for attention.");
        System.out.println("Please, give author 10, if you enjoyed the race :)");
    }
}
