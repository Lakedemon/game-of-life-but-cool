package Game.input;

import Game.paint.Painter;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import static Game.input.Keybindings.*;

public class InputHandler {

    private final Painter painter;

    public InputHandler(Painter painter) {
        this.painter = painter;
    }

    public void registerKeyHandlers(Scene scene, Canvas canvas) {
        registerMouseButtons(scene, canvas);
        registerKeyboardBinds(scene);
    }

    private void registerMouseButtons(Scene scene, Canvas canvas) {
        scene.setOnMouseDragged(e -> this.painter.paint(e, canvas));
        scene.setOnMousePressed(e -> this.painter.paint(e, canvas));

        scene.setOnScroll(painter::handleBrushResize);
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
