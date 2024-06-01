package Game.structures;

import Game.Cell;

public class Structure {
    private Cell[][] grid;
    private int[][] gridValues;

    public Structure(int[][] grid) {
        this.gridValues = grid;
        int gridHeight = grid.length;
        int gridWidth = grid[0].length;
        this.grid = new Cell[gridHeight][gridWidth];

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                this.grid[i][j] = new Cell(grid[i][j]);
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int[][] getGridValues() {
        return gridValues;
    }
}
