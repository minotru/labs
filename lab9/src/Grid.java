import java.util.ArrayList;
import java.util.List;

enum TestResult {
    HIT, MISS, KILL
}

public class Grid
{
    private Location[][] grid;
    List<Ship> ships;

    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;
    
    public Grid() {
        grid = new Location[NUM_ROWS + 2][NUM_COLS + 2];
        for (int row = 0; row <= NUM_ROWS + 1; row++)
            for (int col = 0; col <= NUM_COLS + 1; col++)
                grid[row][col] = Location.FREE;
        ships = new ArrayList<>();
    }

    public boolean isLost() {
        for (Ship ship : ships)
            if (ship.isAlive())
                return false;
        return true;
    }
    
    TestResult testCell(int row, int col) {
        TestResult testResult = TestResult.MISS;
        if (grid[row][col] != Location.SHIP)
            testResult = TestResult.MISS;
        else
            for (Ship ship : ships)
                if (ship.isStandsOn(row, col)) {
                    ship.shoot();
                    markHit(ship, row, col);
                    if (ship.isAlive())
                        testResult = TestResult.HIT;
                    else {
                        testResult = TestResult.KILL;
                        markKill(ship);
                    }
                    break;
                }
        grid[row][col] = Location.USED;
        return testResult;
    }

    public boolean canPlaceShip(Ship ship) {
        int i0, j0, i1, j1;
        i0 = ship.getFirstCell().row;
        j0 = ship.getFirstCell().column;
        i1 = ship.getLastCell().row;
        j1 = ship.getLastCell().column;
        if (i0 < 1 || i0 > NUM_ROWS ||
                j0 < 1 || j0 > NUM_COLS)
            return false;
        for (int i = i0 - 1; i <= i1 + 1; i++)
            for (int j = j0 - 1; j <= j1 + 1; j++)
                if (grid[i][j] != Location.FREE)
                    return false;
        return true;
    }

    private void markHit(Ship ship, int row, int col) {
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, 1, -1};
        for (int i = 0; i < 4; i++)
            grid[row + dr[i]][col + dc[i]] = Location.USED;
    }

    private void markKill(Ship ship) {
        for (int i = ship.getFirstCell().row  - 1; i <= ship.getLastCell().row + 1; i++)
            for (int j = ship.getFirstCell().column - 1; j <= ship.getLastCell().column + 1; j++)
                set(i, j, Location.USED);
    }


    public void addShip(Ship ship) {
        ships.add(ship);
        for (int i = ship.getFirstCell().row; i <= ship.getLastCell().row; i++)
            for (int j = ship.getFirstCell().column; j <= ship.getLastCell().column; j++)
                set(i, j, Location.SHIP);

    }

    public void set(int row, int column, Location loc) {
        grid[row][column] = loc;
    }

    public Location get(int row, int col) {
        return grid[row][col];
    }
}