import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    public static boolean isDebug = true;

    public static Transport[] createTransport() {
        ArrayList<Transport> transports = new ArrayList<>();
        transports.add(new Bus( 1));
        transports.add(new Trolleybus(2));
        transports.add(new Bus(3));
        transports.add(new Bus(8));
        transports.add(new Bus(1998));
        transports.add(new Bus(747133));
        transports.add(new Tram(4));
        transports.add(new Tram(423541));
        transports.add(new Tram(11111));
        return transports.toArray(new Transport[transports.size()]);
    }

    public static Route[] createRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(Transport.Type.BUS, 113, 40, 10));
        routes.add(new Route(Transport.Type.TRAM, 1, 30, 8));
        routes.add(new Route(Transport.Type.TROLLEYBUS, 37, 20, 5));
        return routes.toArray(new Route[routes.size()]);
    }

    static Locale createLocale( String[] args )	{
        if ( args.length == 2 ) {
            return new Locale( args[0], args[1] );
        } else if( args.length == 4 ) {
            return new Locale( args[2], args[3] );
        }
        return null;
    }

    static void setupConsole(String[] args) {
        if ( args.length >= 2 ) {
            if ( args[0].compareTo("-encoding")== 0 ) {
                try {
                    System.setOut( new PrintStream( System.out, true, args[1] ));
                } catch ( UnsupportedEncodingException ex ) {
                    System.err.println( "Unsupported encoding: " + args[1] );
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        setupConsole(args);
        Locale loc = createLocale(args);
        if (loc == null) {
            System.err.println(
                    "Invalid argument(s)\n" +
                            "Syntax: [-encoding ENCODING_ID] language country\n" +
                            "Example: -encoding Cp855 be BY");
            System.exit(1);
        }
        AppLocale.set(loc);
        RouteManager routeManager = new RouteManager(1000);
        Connector connector = new Connector("data.bin");
        try {
            //connector.write(createRoutes(), createTransport());
            connector.read();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
            for (Transport transport : connector.getTransports()) {
                routeManager.addTransport(transport);
                System.out.println(
                        transport.toString() + " " +
                                AppLocale.getString(AppLocale.created) + " " +
                                dateFormat.format(transport.getCreationDate()));

            }
            for (Route route : connector.getRoutes())
                routeManager.addRoute(route);
        } catch (IOException e) {
            System.err.println("Bad file with data");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true)
            routeManager.update();
    }
}
