public class Trolleybus extends Transport {
    public Trolleybus(int number) {
        super(Type.BUS, number);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.trolleybus) + " " + super.toString();
    }
}
