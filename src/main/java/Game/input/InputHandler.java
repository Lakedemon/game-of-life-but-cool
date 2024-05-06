package Game.input;

import Game.GameOfLife;
import Game.graphics.GraphicsHandler;
import Game.paint.Brush;
import Game.paint.BrushShape;
import Game.paint.Painter;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler {

    private final GameOfLife gameOfLife;
    private final Painter painter;

    private final KeyboardActions keyboardActions;
    private final MouseActions mouseActions;

    private final KeyCode CLEAR_BOARD_BIND = KeyCode.SPACE;
    private final KeyCode TOGGLE_BRUSH_TYPE_BIND = KeyCode.T;
    private final KeyCode TOGGLE_PAINT_MODE_BIND = KeyCode.TAB;

    public InputHandler(GameOfLife gameOfLife, Painter painter) {
        this.gameOfLife = gameOfLife;
        this.painter = painter;

        this.keyboardActions = new KeyboardActions();
        this.mouseActions = new MouseActions();
    }

    public void registerKeyHandlers(Scene scene, Canvas canvas) {
        registerMouseButtons(scene, canvas);
        registerKeyboardBinds(scene);
    }

    private void registerMouseButtons(Scene scene, Canvas canvas) {
        scene.setOnMouseDragged(e -> this.painter.paint(e, canvas));
        scene.setOnMousePressed(e -> this.painter.paint(e, canvas));

        scene.setOnScroll(e -> {
            painter.addBrushWidth((int) e.getDeltaY() / 10);
        });
    }

    private void registerKeyboardBinds(Scene scene) {
        scene.setOnKeyPressed(e -> {

            if (e.getCode() == CLEAR_BOARD_BIND) {
                painter.attemptClearBoard();

            } else if (e.getCode() == TOGGLE_BRUSH_TYPE_BIND) {
                painter.attemptToggleBrushType();
            } else if (e.getCode() == TOGGLE_PAINT_MODE_BIND) {
                painter.togglePaintMode();
            }
        });
    }

}
