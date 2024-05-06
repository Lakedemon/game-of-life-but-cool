package Game;

import Game.rules.RuleBook;

public class GameOfLife {
    private final Cell[][] grid;

    private final int gridWidth;
    private final int gridHeight;
    private int CLEAR_VALUE = 0;

    private RuleBook ruleBook;

    public GameOfLife(int gridWidth, int gridHeight, RuleBook ruleBook) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.ruleBook = ruleBook;

        this.grid = new Cell[gridWidth][gridHeight];
        initCellGrid(gridWidth, gridHeight, 0);

        ranFill();
    }

    public void clearBoard() {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                grid[i][j].setValue(CLEAR_VALUE);
                grid[i][j].setNextValue(CLEAR_VALUE);
            }
        }
    }

    public void setSquare(int x, int y, int width, boolean white) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int newX = x + i-width/2;
                int newY = y + j-width/2;

                attemptSetPixel(newY, newX, white ? 1 : 0);
            }
        }
    }

    public void setCircle(int x, int y, int radius, boolean white) {
        int x1 = radius;
        int y1 = 0;
        int dx = 1;
        int dy = 1;
        int err = dx - (radius << 1);

        while (x1 >= y1) {
            attemptSetPixel(y + y1,x + x1, white ? 1 : 0);
            attemptSetPixel(y + x1,x + y1, white ? 1 : 0);
            attemptSetPixel(y + x1,x - y1, white ? 1 : 0);
            attemptSetPixel(y + y1,x - x1, white ? 1 : 0);
            attemptSetPixel(y - y1,x - x1, white ? 1 : 0);
            attemptSetPixel(y - x1,x - y1, white ? 1 : 0);
            attemptSetPixel(y - x1,x + y1, white ? 1 : 0);
            attemptSetPixel(y - y1,x + x1, white ? 1 : 0);

            if (err <= 0) {
                y1++;
                err += dy;
                dy += 2;
            }
            if (err > 0) {
                x1--;
                dx += 2;
                err += dx - (radius << 1);
            }
        }
    }

    private void attemptSetPixel(int x, int y, int val) {
        if (inBounds(x, y)) {
            grid[x][y].setValue(val);
            grid[x][y].setNextValue(val);
        }
    }

    private void initCellGrid(int gridWidth, int gridHeight, int value){
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                grid[i][j] = new Cell(value);
            }
        }

        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                Cell currentCell = grid[i % gridWidth][j % gridHeight];
                currentCell.linkNeighbour(grid[properModulo(i - 1, gridWidth)] [j]                                 );
                currentCell.linkNeighbour(grid[i]                                 [properModulo(j - 1, gridHeight)]);
                currentCell.linkNeighbour(grid[properModulo(i - 1, gridWidth)] [properModulo(j - 1, gridHeight)]);
                currentCell.linkNeighbour(grid[properModulo(i - 1, gridWidth)] [properModulo(j + 1, gridHeight)]);
            }
        }
    }

    int properModulo(int a, int b) {
        return (a + b) % b;
    }

    void ranFill(){
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].setValue(Math.random() >= 0.5 ? 1 : 0);
            }
        }
    }

    void stepGen(){
        for(int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                ruleBook.applyRules(grid[i][j]);
            }
        }

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].stepValue();
            }
        }
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }


    public int[][] getGridValues() {
        int[][] gridValues = new int[gridWidth][gridHeight];

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                gridValues[i][j] = grid[i][j].getValue();
            }
        }

        return gridValues;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGridValues(int[][] grid) {
        for(int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                this.grid[i][j].setValue(grid[i][j]);
                this.grid[i][j].setNextValue(grid[i][j]);
            }
        }
    }
}
