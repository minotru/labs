public class Cell {
    public int column;
    public int row;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return row + " " + column;
    }
}
