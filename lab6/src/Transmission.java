public class Transmission extends Part {
    public Transmission(int power, int price) {
        super(power, price);
    }

    public Transmission(Transmission other) {
        super(other);
    }

    @Override
    public String toString() {
        return "Transmission " + super.toString();
    }
}
