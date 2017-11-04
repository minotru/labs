import java.io.Serializable;
import java.util.Date;

public abstract class Transport implements Serializable {
    private transient Date creationDate;
    private boolean isBroken;
    private int id;
    private Route route;

    public enum Type {BUS, TRAM, TROLLEYBUS}

    protected final Type type;

    protected Transport(Type type, int id) {
        this.type = type;
        this.isBroken = false;
        this.id = id;
        initDate();
    }

    public void initDate() {
        if (creationDate == null)
            creationDate = new Date();
    }

    public void setRoute(Route route) {
        if (this.route != null)
            this.route.removeTransport(this);
        this.route = route;
        if (route != null)
            route.addTransport(this);
    }

    public Route getRoute() {
        return route;
    }

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return (Date) creationDate.clone();
    }

    public void setBroken(boolean isBroken) {
        if (this.isBroken == isBroken)
            return;
        this.isBroken = isBroken;
        if (isBroken == true) {
            setRoute(null);
            System.out.println(toString() + " " + AppLocale.getString(AppLocale.broken));
        }
        else
            System.out.println(toString() + " " + AppLocale.getString(AppLocale.repaired));
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.number) + " " + getId();
    }
}
