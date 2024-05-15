package Game;

import Game.graphics.GraphicsHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {

    private final GameOfLife gameOfLife;
    private final GraphicsHandler graphicsHandler;

    private final Brush brush;
    private final int DEFAULT_WIDTH = 20;
    private final CursorShape DEFAULT_CURSOR_SHAPE = CursorShape.CIRCLE;

    private final KeyCode CLEAR_BOARD_BIND = KeyCode.SPACE;
    private final KeyCode RANDOM_BOARD_BIND = KeyCode.R;
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
        scene.setOnMouseDragged(e -> {
            if (!paintMode) return;

            double x = (canvas.getTranslateX() + e.getX()) / graphicsHandler.getCellSize();
            double y = (canvas.getTranslateY() + e.getY()) / graphicsHandler.getCellSize();

            if (this.brush.shape.equals(CursorShape.CIRCLE))
                gameOfLife.setCircle((int) x, (int) y, brush.width / 2, !e.isSecondaryButtonDown());
            else if (this.brush.shape.equals(CursorShape.SQUARE))
                gameOfLife.setSquare((int) x, (int) y, brush.width, !e.isSecondaryButtonDown());
        });

        scene.setOnScroll(e -> {
            if (!paintMode) return;

            this.brush.width += (int) e.getDeltaY() / 10;
            this.brush.clampWidth();

            this.graphicsHandler.resizeCustomCursor(e, this.brush.width);
        });
    }

    private void registerKeyboardBinds(Scene scene) {
        scene.setOnKeyPressed(e -> {
            handleTogglePaintMode(e);

            if (paintMode) {
                handleClearBoard(e);
                handleResetBoard(e);
                handleToggleBrushType(e);
            }
        });
    }

    private void handleClearBoard(KeyEvent e) {
        if (e.getCode() == CLEAR_BOARD_BIND) {
            gameOfLife.clearBoard();
        }
    }
    private void handleResetBoard(KeyEvent e){
        if (e.getCode() == RANDOM_BOARD_BIND) {
            gameOfLife.ranFill();
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

    public enum CursorShape {
        SQUARE(false),
        CIRCLE(true);

        public CursorShape next() {
            return CursorShape.values()[(this.ordinal() + 1) % CursorShape.values().length];
        }

        public boolean isCircular() {
            return this.circular;
        }

        private final boolean circular;

        private CursorShape(boolean circular) {
            this.circular = circular;
        }
    } // TODO: FIX

    public class Brush {

        private final int MIN_WIDTH = 2, MAX_WIDTH = 50;

        int width;
        CursorShape shape;

        public Brush(int width, CursorShape shape) {
            this.width = width;
            this.shape = shape;
        }

        public void clampWidth() {
            if (this.width < MIN_WIDTH) {
                this.width = MIN_WIDTH;
            } else if (this.width > MAX_WIDTH) {
                this.width = MAX_WIDTH;
            }
        }

        public boolean isCircular() {
            return this.shape.equals(CursorShape.CIRCLE);
        }

        public int getWidth() {
            return this.width;
        }

    }

}
