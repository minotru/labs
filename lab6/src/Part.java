public class Part {
    private int price;
    private int power;

    public Part(int power, int price) {
        setPower(power);
        setPrice(price);
    }

    public Part(Part other) {
        this(other.power, other.price);
    }

    public Part() {
        this(0, 0);
    }


    public void setPower(int power) {
        if (price < 0)
            throw new IllegalArgumentException("Price can't be negative");
        this.power = power;
    }

    public void setPrice(int price) {
        if (price < 0)
            throw new IllegalArgumentException("Price can't be negative");
        this.price = price;
    }

    @Override
    public String toString() {
        return "power: " + getPower() + ", price: " + getPrice();
    }

    public int getPrice() {
        return price;
    }

    int getPower() {
        return power;
    }
}