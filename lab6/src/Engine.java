public class Engine extends Part {
    public Engine(int power, int price) {
        super(power, price);
    }

    public Engine(Engine other) {
        super(other);
    }

    @Override
    public String toString() {
        return "Engine " + super.toString();
    }
}
