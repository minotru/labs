import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static boolean isRunning = true;
    private static int runningCnt = 2;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(Constants.PORT);
        System.out.println("Server is running");
        Socket socket1 = null;
        Socket socket2 = null;
        try {
            while (socket1 == null || socket2 == null) {
                if (socket1 == null)
                    socket1 = listener.accept();
                else
                    socket2 = listener.accept();
            }
            System.out.println("1 connected");
            System.out.println("2 connected");
            Handler player1 = new Handler(socket1);
            Handler player2 = new Handler(socket2);
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            player1.start();
            player2.start();
            while (runningCnt > 0) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket1 != null)
                socket1.close();
            if (socket2 != null)
                socket2.close();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private Handler opponent;
        private boolean isReady = false;

        public void setOpponent(Handler opponent) {
            this.opponent = opponent;
        }

        public Handler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String line;
                opponent.out.println(Protocol.OPPONENT_CONNECTED);
                while (true) {
                    String input = in.readLine();
                    String command = new Scanner(input).next();
                    switch (command) {
                        case Protocol.READY:
                            if (opponent.isReady) {
                                out.println(Protocol.ASK_TURN);
                                opponent.out.println(Protocol.OPPONENT_TURN);
                            } else
                                isReady = true;
                            break;
                        case Protocol.TURN_RESULT:
                            opponent.out.println(input);
                            opponent.out.println(Protocol.OPPONENT_TURN);
                            out.println(Protocol.ASK_TURN);
                            break;
                        default:
                            opponent.out.println(input);
                    }

                    if (input == null) {
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                runningCnt--;
            }
        }
    }

}
