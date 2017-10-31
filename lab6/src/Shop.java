import java.util.*;

public class Shop {
    private ArrayList<Good> engines;
    private ArrayList<Good> transmissions;
    private ArrayList<Good> tyres;

    private class Good {
        public final Part part;
        public int quantity;

        Good(Part part, int quantity) {
            this.part = part;
            this.quantity = quantity;
        }
    }

    public Shop() {
        engines = new ArrayList<Good>();
        transmissions = new ArrayList<Good>();
        tyres =  new ArrayList<Good>();
    }

    public void addGood(Part part, int quantity) {
        Good good = new Good(part, quantity);
        if (part instanceof  Engine)
            engines.add(good);
        else if (part instanceof Transmission)
            transmissions.add(good);
        else
            tyres.add(good);
    }

    public List<Part> buyRandomSet(int budget) {
        Random random =  new Random();
        ArrayList<Part> set = new ArrayList<>();
        int cnt;
        Good good;
        boolean bought;
        if (!engines.isEmpty()) {
            bought = false;
            cnt = 0;
            while (cnt < 3 && !bought) {
                good = engines.get(random.nextInt(engines.size()));
                cnt++;
                if (good.quantity > 0 && good.part.getPrice() <= budget) {
                    good.quantity--;
                    budget -= good.part.getPrice();
                    set.add(new Engine((Engine) good.part));
                    bought = true;
                }
            }
        }
        if (!transmissions.isEmpty()) {
            good = transmissions.get(random.nextInt(transmissions.size()));
            bought = false;
            cnt = 0;
            while (cnt < 3 && !bought) {
                cnt++;
                if (good.quantity > 0 && good.part.getPrice() <= budget) {
                    good.quantity--;
                    budget -= good.part.getPrice();
                    set.add(new Transmission((Transmission) good.part));
                    bought = true;
                }
            }
        }
        if (!tyres.isEmpty()) {
            good = tyres.get(random.nextInt(engines.size()));
            bought = false;
            cnt = 0;
            while (cnt < 3 && !bought) {
                cnt++;
                if (good.quantity > 0 && good.part.getPrice() <= budget) {
                    bought = true;
                    good.quantity--;
                    budget -= good.part.getPrice();
                    set.add(new Tyres((Tyres) good.part));
                }
            }
        }
        return set;
    }
}
