package Game.ui.impl;

import Game.Cell;
import Game.ui.GuiComponent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static Game.Main.CELL_SIZE;

public class GameOfLifeGuiComponent extends GuiComponent {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final StackPane parentPane;

    private final Color DEFAULT_LIGHT_COLOR = Color.grayRgb(200);
    private final Color DEFAULT_DARK_COLOR = Color.grayRgb(40);

    public GameOfLifeGuiComponent(double scale) {
        int width = (int) scale * CELL_SIZE, height = (int) scale * CELL_SIZE;

        this.parentPane = new StackPane();
        canvas = new Canvas(width, height);

        this.gc = canvas.getGraphicsContext2D();
        this.gc.setFill(DEFAULT_DARK_COLOR);

        this.parentPane.getChildren().add(canvas);

        this.parentPane.setMinHeight(canvas.getHeight());
        this.parentPane.setMinWidth(canvas.getWidth());
        this.parentPane.setMaxHeight(canvas.getHeight());
        this.parentPane.setMaxWidth(canvas.getWidth());
    }

    public void refreshGameOfLifeCanvas(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getValue() == 0) {
                    gc.setFill(DEFAULT_DARK_COLOR);
                } else {
                    gc.setFill(DEFAULT_LIGHT_COLOR);
                }
                gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public Node getDrawableElement() {
        return this.parentPane;
    }
}
