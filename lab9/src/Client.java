import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            if (args.length == 0)
                socket = new Socket("127.0.0.1", Constants.PORT);
            else
                socket = new Socket(args[0], Constants.PORT);
        } catch (IOException e) {
            System.err.println("Can not connect to the server");
            return;
        }

        Player player = new Player(socket);
        player.start();
    }
}
