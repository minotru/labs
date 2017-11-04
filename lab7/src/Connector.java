import java.io.*;

public class Connector {

    private String filename;
    private Route[] routes;
    private Transport[] transports;

    public Connector( String filename ) {
        this.filename = filename;
    }


    public Route[] getRoutes() {
        return  routes;
    }

    public Transport[] getTransports() {
        return transports;
    }

    public void write(Route[] routes, Transport[] transports) throws IOException {
        FileOutputStream fos = new FileOutputStream (filename);
        try ( ObjectOutputStream oos = new ObjectOutputStream( fos )) {
            oos.writeInt( routes.length );
            for ( int i = 0; i < routes.length; i++) {
                oos.writeObject( routes[i] );
            }
            oos.writeInt( transports.length );
            for ( int i = 0; i < transports.length; i++) {
                oos.writeObject( transports[i] );
            }
            oos.flush();
        }
    }

    public void read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        try ( ObjectInputStream oin = new ObjectInputStream(fis)) {
            routes = new Route[oin.readInt()];
            for ( int i = 0; i < routes.length; i++ )
                routes[i] = (Route) oin.readObject();
            transports = new Transport[oin.readInt()];
            for ( int i = 0; i < transports.length; i++ ) {
                transports[i] = (Transport) oin.readObject();
                transports[i].initDate();
            }
        }
    }
}