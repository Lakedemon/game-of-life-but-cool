package Game;

import Game.graphics.GraphicsHandler;
import Game.paint.Brush;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.beans.EventHandler;

public class InputHandler {

    private final GameOfLife gameOfLife;
    private final GraphicsHandler graphicsHandler;

    private final Brush brush;
    private final int DEFAULT_WIDTH = 20;
    private final BrushShape DEFAULT_CURSOR_SHAPE = BrushShape.CIRCLE;

    private final KeyCode CLEAR_BOARD_BIND = KeyCode.SPACE;
    private final KeyCode TOGGLE_BRUSH_TYPE_BIND = KeyCode.T;
    private final KeyCode TOGGLE_PAINT_MODE_BIND = KeyCode.TAB;

    private boolean paintMode = true;

    public InputHandler(GameOfLife gameOfLife, GraphicsHandler graphicsHandler) {
        this.gameOfLife = gameOfLife;
        this.graphicsHandler = graphicsHandler;

        this.brush = new Brush(DEFAULT_WIDTH, DEFAULT_CURSOR_SHAPE);
    }

    public void registerKeyHandlers(Scene scene, Canvas canvas) {
        registerMouseButtons(scene, canvas);
        registerKeyboardBinds(scene);
    }

    private void registerMouseButtons(Scene scene, Canvas canvas) {
        scene.setOnMouseDragged(e -> paint(e, canvas));
        scene.setOnMousePressed(e -> paint(e, canvas));

        scene.setOnScroll(e -> {
            if (!paintMode) return;

            this.brush.width += (int) e.getDeltaY() / 10;
            this.brush.clampWidth();

            this.graphicsHandler.resizeCustomCursor(e, this.brush.width);
        });
    }

    private void paint(MouseEvent e, Canvas canvas) {
        if (!paintMode) return;

        double x = (canvas.getTranslateX() + e.getX()) / graphicsHandler.getCellSize();
        double y = (canvas.getTranslateY() + e.getY()) / graphicsHandler.getCellSize();

        if (this.brush.shape.equals(BrushShape.CIRCLE))
            gameOfLife.setCircle((int) x, (int) y, brush.width / 2, !e.isSecondaryButtonDown());
        else if (this.brush.shape.equals(BrushShape.SQUARE))
            gameOfLife.setSquare((int) x, (int) y, brush.width, !e.isSecondaryButtonDown());
    }

    private void registerKeyboardBinds(Scene scene) {
        scene.setOnKeyPressed(e -> {
            handleTogglePaintMode(e);

            if (paintMode) {
                handleClearBoard(e);
                handleToggleBrushType(e);
            }
        });
    }

    private void handleClearBoard(KeyEvent e) {
        if (e.getCode() == CLEAR_BOARD_BIND) {
            gameOfLife.clearBoard();
        }
    }
    private void handleToggleBrushType(KeyEvent keyEvent) {
        if (keyEvent.getCode() == TOGGLE_BRUSH_TYPE_BIND) {
            this.brush.shape = this.brush.shape.next();
            this.graphicsHandler.setCursorShape(this.brush.shape);
        }
    }
    private void handleTogglePaintMode(KeyEvent keyEvent) {
        if (keyEvent.getCode() == TOGGLE_PAINT_MODE_BIND) {
            this.paintMode = !this.paintMode;
            this.graphicsHandler.toggleCustomCursor();
        }
    }

    public Brush getBrush() {
        return this.brush;
    }

    public enum BrushShape {
        SQUARE(false),
        CIRCLE(true);

        public BrushShape next() {
            return BrushShape.values()[(this.ordinal() + 1) % BrushShape.values().length];
        }

        public boolean isCircular() {
            return this.circular;
        }

        private final boolean circular;

        private BrushShape(boolean circular) {
            this.circular = circular;
        }
    }



}
