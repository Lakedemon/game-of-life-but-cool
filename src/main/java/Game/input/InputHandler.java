package Game.input;

import Game.paint.Painter;
import Game.ui.GuiComponent;
import Game.ui.GuiHandler;
import Game.ui.impl.GameOfLifeGuiComponent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

import static Game.input.Keybindings.*;

public class InputHandler {

    // Dependencies
    private final Painter painter;

    // Store
    private final Set<GuiComponent> mouseOver;

    public InputHandler(Painter painter) {
        this.mouseOver = new HashSet<>();
        this.painter = painter;
    }

    public void registerKeyHandlers(Scene scene, GuiHandler guiHandler) {
        GameOfLifeGuiComponent gameOfLifeComponent = guiHandler.getGameOfLifeGuiComponent();

        registerGameOfLifeMouseButtons(gameOfLifeComponent);
        registerGameOfLifeKeyboardBinds(scene, gameOfLifeComponent);
    }

    private void registerGameOfLifeMouseButtons(final GameOfLifeGuiComponent gameOfLifeComponent) {
        Canvas gameOfLifeCanvas = (Canvas) gameOfLifeComponent.getDrawableElement();
        EventHandler<MouseEvent> paintHandler = generatePaintEventHandler();

        gameOfLifeCanvas.setOnMouseDragged(paintHandler);
        gameOfLifeCanvas.setOnMousePressed(paintHandler);

        gameOfLifeCanvas.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            mouseOver.add(gameOfLifeComponent);
            painter.setPaintMode(true);
        });
        gameOfLifeCanvas.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            mouseOver.remove(gameOfLifeComponent);
            painter.setPaintMode(false);
        });

        gameOfLifeCanvas.setOnScroll(painter::handleBrushResize);
    }

    private EventHandler<MouseEvent> generatePaintEventHandler() {
        return e -> painter.paint(e.getX(), e.getY(), e.isSecondaryButtonDown());
    }

    private void registerGameOfLifeKeyboardBinds(final Scene scene, final GameOfLifeGuiComponent gameOfLifeComponent) {
        scene.setOnKeyPressed(e -> {
            if (!this.mouseOver.contains(gameOfLifeComponent)) return;

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
