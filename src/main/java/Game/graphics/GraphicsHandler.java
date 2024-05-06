package Game.graphics;

import Game.Cell;
import Game.InputHandler;
import Game.paint.Brush;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static Game.Main.CELL_SIZE;

public class GraphicsHandler {

    private final GraphicsContext gameGraphics;

    private DrawingCursor drawingCursor;
    private boolean cursorToggled;

    public GraphicsHandler(Canvas gameOfLifeCanvas) {
        this.gameGraphics = gameOfLifeCanvas.getGraphicsContext2D();
    }

    public void initCustomCursor(Scene scene, Pane root, Brush brush) {
        this.drawingCursor = new DrawingCursor(scene, root, brush.getWidth(), brush.getWidth());

        if (brush.isCircular()) {
            this.drawingCursor.makeCircle();
        }
        this.drawingCursor.setWidth(brush.getWidth());
        this.drawingCursor.setColor(Color.AQUA);

        this.drawingCursor.activate();
        this.cursorToggled = true;
    }

    public void resizeCustomCursor(ScrollEvent scrollEvent, int newWidth) {
        this.drawingCursor.setWidth(newWidth);
        this.drawingCursor.setHotSpotX(newWidth);
        this.drawingCursor.setHotSpotY(newWidth);

        this.drawingCursor.reloadPosition(scrollEvent);
    }

    public void toggleCustomCursor() {
        if (cursorToggled) {
            this.drawingCursor.unRegister();
            cursorToggled = false;
        } else {
            this.drawingCursor.reRegister();
            cursorToggled = true;
        }
    }

    public void setCursorShape(InputHandler.CursorShape cursorShape) {
        int currentWidth = this.drawingCursor.getWidth();
        Color currentColor = this.drawingCursor.getColor();

        switch (cursorShape) {
            case CIRCLE:
                this.drawingCursor.makeCircle();
                break;
            case SQUARE:
                this.drawingCursor.makeSquare();
                break;
        }

        this.drawingCursor.setWidth(currentWidth);
        this.drawingCursor.setColor(currentColor);

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
