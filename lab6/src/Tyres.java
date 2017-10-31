public class Tyres extends  Part {
    public Tyres(int power, int price) {
        super(power, price);
    }

    public Tyres(Tyres other) {
        super(other);
    }

    @Override
    public String toString() {
        return "Tyres " + super.toString();
    }
}
