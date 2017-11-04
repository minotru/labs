import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Route implements Serializable{
    private final int number;
    private final Transport.Type type;
    private final double routeTime;
    private final double bestInterval;
    private List<Transport> transports;
    private Date lastMoved;

    public Route(
            Transport.Type type,
            int number,
            double routeTime,
            double bestInterval) {
        this.type = type;
        this.number = number;
        this.routeTime = routeTime;
        this.bestInterval = bestInterval;
        this.transports = new LinkedList<Transport>();
    }

    public Date getLastMoved() {
        return lastMoved;
    }

    public void move() {
        lastMoved = new Date();
        System.out.println(toString() + " " + AppLocale.getString(AppLocale.hasCome));
    }

    public double getInterval() {
        return routeTime / transports.size();
    }

    public boolean hasEnoughtTransport() {
        return getInterval() <= bestInterval;
    }

    public void addTransport(Transport transport) {
        transports.add(transport);
    }

    public void removeTransport(Transport transport) {
        transports.remove(transport);
    }

    public int getNumber() {
        return number;
    }

    public Transport.Type getType() {
        return type;
    }

    @Override
    public String toString() {
        String transportType = "";
        switch (getType()) {
            case BUS:
                transportType = AppLocale.getString(AppLocale.bus);
                break;
            case TROLLEYBUS:
                transportType = AppLocale.getString(AppLocale.trolleybus);
                break;
            case TRAM:
                transportType = AppLocale.getString(AppLocale.tram);
                break;
        }
        return transportType + " " + AppLocale.getString(AppLocale.number) + " " + number;
    }
}
