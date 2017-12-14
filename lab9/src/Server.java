import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class Server {
    private static Socket waitingSocket;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(Constants.PORT);
        System.out.println("Server is running");
        try {
            Socket socket;
            while (true) {
                socket = listener.accept();
                System.out.println("Player connected");
                if (waitingSocket == null)
                    waitingSocket = socket;
                else {
                    Handler player1 = new Handler(socket);
                    Handler player2 = new Handler(waitingSocket);
                    player1.setOpponent(player2);
                    player2.setOpponent(player1);
                    player1.start();
                    player2.start();
                    waitingSocket = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

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
            } catch (SocketException e) {
                opponent.out.println(Protocol.OPPONENT_DISCONNECTED);
            }catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }
        }
    }

}
