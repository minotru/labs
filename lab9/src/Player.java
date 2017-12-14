import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player
{
    private static final int[] SHIPS_SETTINGS = {2};
    private Scanner input = new Scanner(System.in);
    private Socket socket;
    private BufferedReader serverIn;
    private PrintWriter serverOut;

    public Grid playerGrid;
    public Grid enemyGrid;
    
    public Player(Socket socket)
    {
        playerGrid = new Grid();
        enemyGrid = new Grid();
        this.socket = socket;
        try {
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char locationToCharacter(Location location) {
        if (location == Location.FREE)
            return '-';
        else if (location == Location.USED)
            return '#';
        else
            return '^';
    }

    public void printGrid(Grid grid) {
        System.out.print("\t ");
        for (int j = 1; j <= Grid.NUM_ROWS; j++)
            System.out.print(j + "\t");
        System.out.println();
        for (int i = 1; i <= Grid.NUM_ROWS; i++) {
            System.out.print(i + "\t|");
            for (int j = 1; j <= Grid.NUM_COLS; j++) {
                System.out.print(locationToCharacter(grid.get(i, j)) + "\t");
            }
            System.out.println();
        }
    }

    private Ship readShip(int length) {
        int row = 0, column = 0, direction = 0;
        System.out.println("Place your " + length + "-decked ship");
        boolean isValid;
        do {
            isValid = true;
            try {
                System.out.println("Enter row");
                row = input.nextInt();
                System.out.println("Enter column");
                column = input.nextInt();
                System.out.println("Enter direction (0 - horizontal, 1 - vertical)");
                direction = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid format");
                isValid = false;
            }
        } while (!isValid);
        return new Ship(length,  row, column, direction);
    }


    private Cell askTurn() {
        int row, column;
        boolean isValid = false;
        System.out.println("Enter next turn row and column");
        do {
            row = input.nextInt();
            column = input.nextInt();
            if (row < 1 || row > Grid.NUM_ROWS ||
                    column < 1 || column > Grid.NUM_COLS)
                System.out.println("Invalid position");
            else
                isValid = true;
        } while (!isValid);
        return new Cell(row, column);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printGrids() {
       // clearScreen();
        System.out.println("Your grid");
        printGrid(playerGrid);
        System.out.println("Enemy's grid");
        printGrid(enemyGrid);
    }

    public void start() throws IOException{
        String line = null;
        Scanner sc = null;
        System.out.println("Waiting for the opponent...");
        line = serverIn.readLine();
        if (line.startsWith(Protocol.OPPONENT_CONNECTED)) {
            System.out.println("Opponent connected");
            placeShips();
            serverOut.println(Protocol.READY);
            System.out.println("Wait for your opponent...");
        }
        boolean isRunning = true;
        while (isRunning) {
            //clearScreen();
            sc = new Scanner(serverIn.readLine());
            String command = sc.next();
            if (command.equals(Protocol.ASK_TURN)) {
                Cell turn = askTurn();
                serverOut.println(Protocol.TURN + " " + turn);
            }
            else if (command.equals(Protocol.TURN)) {
                int row = sc.nextInt(), column = sc.nextInt();
                TestResult result = playerGrid.testCell(row, column);
                if (playerGrid.isLost()) {
                    serverOut.println(Protocol.GAME_OVER);
                    isRunning = false;
                    System.out.println("You lose :(((");
                }
                else
                    serverOut.println(Protocol.TURN_RESULT + " " + row + " "+ column + " " + result.name());
            } else if (command.equals(Protocol.OPPONENT_TURN)) {
                System.out.println("Your opponent is doing his turn...");
            } else if (command.equals(Protocol.OPPONENT_DISCONNECTED)) {
                System.out.println("Your opponent has diconnected");
                isRunning = false;
            } else if (command.equals(Protocol.TURN_RESULT)){
                int row = sc.nextInt(), column = sc.nextInt();
                TestResult result = TestResult.valueOf(sc.next());
                if (result == TestResult.MISS) {
                    //System.out.println("You've missed");
                    enemyGrid.set(row, column, Location.USED);
                } else  {
                    enemyGrid.set(row, column, Location.SHIP);
//                    if (result == TestResult.HIT)
//                        System.out.println("You've hit");
                    if (result == TestResult.KILL)
                        System.out.println("Yeaaah, you killed!");
                }
                printGrids();
            } else if (command.equals(Protocol.GAME_OVER)) {
                System.out.println("Yeaaaaah, you win!!!");
                isRunning = false;
            }
        }
    }

    public void placeShips() {
        System.out.println("Place your ships");
        for (int i = 0; i < SHIPS_SETTINGS.length; i++) {
            printGrid(playerGrid);
            int length = SHIPS_SETTINGS[i];
            Ship ship = readShip(length);
            while (!playerGrid.canPlaceShip(ship)) {
                System.out.println("Can not place ship to that position");
                ship = readShip(length);
            }
            playerGrid.addShip(ship);
            System.out.println("Placed");
        }
    }
}