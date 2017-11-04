public class Tram extends Transport {
    public Tram(int number) {
        super(Type.TRAM, number);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.tram) + " " + super.toString();
    }
}
