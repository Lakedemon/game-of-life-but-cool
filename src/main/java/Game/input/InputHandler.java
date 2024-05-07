package Game.input;

import Game.paint.Painter;
import Game.ui.GuiHandler;
import Game.ui.impl.GameOfLifeGuiComponent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import static Game.input.Keybindings.*;

public class InputHandler {

    private final Painter painter;

    public InputHandler(Painter painter) {
        this.painter = painter;
    }

    public void registerKeyHandlers(GuiHandler guiHandler) {
        Canvas gameOfLifeCanvas = (Canvas) guiHandler.getGameOfLifeGuiComponent().getDrawableElement();

        registerGameOfLifeMouseButtons(gameOfLifeCanvas);
        registerKeyboardBinds(gameOfLifeCanvas);
    }

    private void registerGameOfLifeMouseButtons(final Canvas gameOfLifeCanvas) {
        EventHandler<MouseEvent> paintHandler = generatePaintEventHandler();

        gameOfLifeCanvas.setOnMouseDragged(paintHandler);
        gameOfLifeCanvas.setOnMousePressed(paintHandler);

        gameOfLifeCanvas.setOnScroll(painter::handleBrushResize);
    }

    private EventHandler<MouseEvent> generatePaintEventHandler() {
        return e -> painter.paint(e.getX(), e.getY(), e.isSecondaryButtonDown());
    }

    private void registerKeyboardBinds(final Canvas gameOfLifeCanvas) {
        gameOfLifeCanvas.setOnKeyPressed(e -> {
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
