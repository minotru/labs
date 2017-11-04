public class Bus extends Transport {
    public Bus(int number) {
        super(Type.BUS, number);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.bus) + " " + super.toString();
    }
}
