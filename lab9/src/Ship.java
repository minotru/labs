import java.util.ArrayList;
import java.util.List;

public class Ship
{
    /* Instance Variables */
    private int row;
    private int col;
    private int length;
    private int direction;
    private int health;
    
    // Direction Constants
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    // Constructor
    public Ship(int length, int row, int col, int direction)
    {
        this.length = length;
        this.health = length;
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    public void shoot() {
        health--;
    }

    public Cell getFirstCell() {
        return new Cell(row, col);
    }

    public Cell getLastCell() {
        if (direction == HORIZONTAL)
            return new Cell(row, col + length - 1);
        else
            return new Cell(row + length - 1, col);
    }

    public List<Cell> getOccupiedCells() {
        List<Cell> cells = new ArrayList<>();
        if (direction == HORIZONTAL)
            for (int l = 0; l < length; l++)
                cells.add(new Cell(row, col + l));
        else
            for (int l = 0; l < length; l++)
                cells.add(new Cell(row + l, col));
        return cells;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getColumn() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getLength() {
        return length;
    }

    public boolean isStandsOn(int testRow, int testCol) {
        if (direction == HORIZONTAL)
            return row == testRow && testCol >= col && testCol <= col + length - 1;
        else
            return col == testCol && testRow >= row && testRow <= row + length - 1;
    }

    // Getter for the direction
    public int getDirection()
    {
        return direction;
    }
}