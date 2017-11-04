import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteManager {
    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;
    private final long timeInOneInterval;
    private long lastRandomActionTime = -1;

    public void move() {
        Date now = new Date();
        for (Route route : routes)
            if (route.getLastMoved() == null ||
                    now.getTime() - route.getLastMoved().getTime() >= route.getInterval() * timeInOneInterval)
                route.move();

    }

    public void update() {
        if (new Date().getTime() - lastRandomActionTime > timeInOneInterval) {
            lastRandomActionTime = new Date().getTime();
            boolean needToBreak = Math.random() > 0.95;
            if (needToBreak) {
                Transport transport = transports.get((int) (Math.random() * transports.size()));
                transport.setBroken(true);
                Route route = transport.getRoute();
                if (route != null)
                    fillRoute(route);
            }
            boolean needToRepair = Math.random() > 0.95;
            if (needToRepair)
                for (Transport transport : transports)
                    if (transport.isBroken()) {
                        transport.setBroken(false);
                        fillRoutes();
                        break;
                    }
        }
        move();
    }


    private void fillRoutes() {
        for (Route route : routes)
            if (!route.hasEnoughtTransport())
                fillRoute(route);
    }

    private void fillRoute(Route route) {
        for (Transport transport : transports)
            if (route.hasEnoughtTransport())
                break;
            else if (transport.getType() == route.getType() &&
                    transport.getRoute() == null) {
                transport.setRoute(route);
            }
    }

    public final List<Transport> getTransports() {
        return transports;
    }


    public RouteManager(long timeInOneInterval) {
        routes = new ArrayList<>();
        transports = new ArrayList<>();
        this.timeInOneInterval = timeInOneInterval;
    }

    public void addTransport(Transport transport) {
        transports.add(transport);
    }

    public void addRoute(Route route) {
        routes.add(route);
        fillRoute(route);
    }
 }