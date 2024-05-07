package Game.ui.impl;

import Game.Cell;
import Game.Main;
import Game.ui.GuiComponent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Game.Main.CELL_SIZE;

public class GameOfLifeGuiComponent extends GuiComponent {

    private final Canvas canvas;
    private GraphicsContext gc;

    private final Color DEFAULT_LIGHT_COLOR = Color.WHITE;
    private final Color DEFAULT_DARK_COLOR = Color.BLACK;

    public GameOfLifeGuiComponent(double scale) {
        canvas = new Canvas(scale * Main.CELL_SIZE, scale * Main.CELL_SIZE);
        this.gc = canvas.getGraphicsContext2D();
        this.gc.setFill(DEFAULT_DARK_COLOR);
    }

    public void refreshGameOfLifeCanvas(int width, int height, Cell[][] grid) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j].getValue() == 0) {
                    gc.setFill(DEFAULT_DARK_COLOR);
                } else {
                    gc.setFill(DEFAULT_LIGHT_COLOR);
                }
                gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    @Override
    public Node getDrawableElement() {
        return canvas;
    }
}
