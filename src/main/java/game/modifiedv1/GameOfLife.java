package game.modifiedv1;

public class GameOfLife {
    public int[][] getGrid() {
        return grid;
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

    public void clearBoard() {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public void setSquare(int x, int y, int width, boolean white) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int newX = x + i-width/2;
                int newY = y + j-width/2;

                if (inBounds(newX, newY)) {
                    grid[newY][newX] = white ? 1 : 0;
                }
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
            attemptSetPixel(y + y1, x + x1, white ? 1 : 0);
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
            grid[x][y] = val;
        }
    }

    public GameOfLife(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        this.grid = new int[gridWidth][gridHeight];
        ranFill();
    }

    private void ranFill() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = Math.random() >= 0.5 ? 1 : 0;
            }
        }
    }

    private int getNeighbours(int x, int y) {
        int neighbours = 0;

        for (int[] i : neis) {
            int x1 = x + i[0];
            int y1 = y + i[1];

            if (inBounds(x1, y1)) {
                if (grid[x1][y1] == 1) {
                    neighbours++;
                }
            }
        }

        return neighbours;
    }

    public void stepGen() {

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

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }
}
