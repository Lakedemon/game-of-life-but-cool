package Game.graphics;

import Game.Cell;
import Game.paint.Brush;
import Game.paint.BrushShape;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static Game.Main.CELL_SIZE;

public class GraphicsHandler {

    private final GraphicsContext gameGraphics;
    private final CursorGraphicsHandler cursorGraphics;


    public GraphicsHandler(Canvas gameOfLifeCanvas) {
        this.cursorGraphics = new CursorGraphicsHandler();
        this.gameGraphics = gameOfLifeCanvas.getGraphicsContext2D();
    }

    public CursorGraphicsHandler getCursorGraphics() {
        return cursorGraphics;
    }

    public void fillGameCanvas(int width, int height, Cell[][] grid) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j].getValue() == 0) {
                    gameGraphics.setFill(javafx.scene.paint.Color.BLACK);
                } else {
                    gameGraphics.setFill(Color.WHITE);
                }
                gameGraphics.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

    }

    public int getCellSize() {
        return CELL_SIZE;
    }
}
