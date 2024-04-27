package Game;

public class GameOfLife {
    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    private int[][] grid;
    private final int gridWidth;
    private final int gridHeight;

    static int[][] neis = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };

    GameOfLife(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        this.grid = new int[gridWidth][gridHeight];
        ranFill();
    }

    void ranFill(){
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = Math.random() >= 0.5 ? 1 : 0;
            }
        }
    }

    int getNeighbours(int x, int y) {
        int neighbours = 0;

        for (int[] i : neis) {
            int x1 = x + i[0];
            int y1 = y + i[1];

            if(inBounds(x1, y1)) {
                if(grid[x1][y1] == 1) {
                    neighbours++;
                }
            }
        }

        return neighbours;
    }

    void stepGen(){

        int[][] newGrid = new int[gridWidth][];
        for (int i = 0; i < gridWidth; i++) {
            newGrid[i] = grid[i].clone();
        }

        for(int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                int neighbours = getNeighbours(i, j);

                if (grid[i][j] == 1) {
                    if (!(neighbours == 2 || neighbours == 3)) {
                        newGrid[i][j] = 0;
                    }
                } else if (neighbours == 3) {
                    newGrid[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < gridWidth; i++) {
            grid[i] = newGrid[i].clone();
        }
    }

    boolean inBounds(int x, int y) {
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }
}
