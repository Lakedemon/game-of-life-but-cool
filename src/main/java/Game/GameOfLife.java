package Game;

import Game.rules.RuleBook;

public class GameOfLife {
    private final Cell[][] grid;

    private final int gridWidth;
    private final int gridHeight;
    private final int CLEAR_VALUE = 0;

    private RuleBook ruleBook;

    public GameOfLife(int gridWidth, int gridHeight, RuleBook ruleBook) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.ruleBook = ruleBook;

        this.grid = new Cell[gridWidth][gridHeight];
        initCellGrid(gridWidth, gridHeight, CLEAR_VALUE);

        ranFill();
    }

    public RuleBook getRuleBook() {
        return ruleBook;
    }

    public void clearBoard() {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                grid[i][j].setValue(CLEAR_VALUE);
            }
        }
    }

    public void setSquare(int x, int y, int width, boolean white) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int newX = x + i-width/2;
                int newY = y + j-width/2;

                attemptSetPixel(newX, newY, white ? 1 : 0);
            }
        }
    }

    public void setCircle(int x, int y, int radius, boolean white) {
        for (int offsetX = -radius; offsetX < radius; offsetX++) {
            for (int offsetY = -radius; offsetY < radius; offsetY++) {
                if (Math.sqrt(Math.pow(offsetX, 2) + Math.pow(offsetY, 2)) <= radius) {
                    attemptSetPixel(x + offsetX, y + offsetY, white ? 1 : 0);
                }
            }
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

    public void ranFill(){
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].setValue(Math.random() >= 0.5 ? 1 : 0);
            }
        }
    }

    public void stepGen(){
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

    private int properModulo(int a, int b) {
        return (a + b) % b;
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

    private void attemptSetPixel(int x, int y, int val) {
        grid[properModulo(y, gridHeight)][properModulo(x, gridWidth)].setValue(val);
    }

    /*private void attemptSetPixel(int x, int y, int val) {
        if(inBounds(x, y)) {
            grid[x][y].setValue(val);
        }
    }*/
}