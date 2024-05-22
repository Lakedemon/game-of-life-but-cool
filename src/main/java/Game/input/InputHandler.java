package Game.input;

import Game.paint.Painter;
import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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

    public void registerKeyHandlers(Scene scene, GuiManager guiManager) {
        GameOfLifeGuiComponent gameOfLifeComponent = guiManager.getGameOfLifeGuiComponent();

        registerMenuMouseButtons(scene, guiManager);
        registerGameOfLifeMouseButtons(gameOfLifeComponent);

        scene.setOnKeyPressed(e -> {
            handleMenuKeyboardBindings(e, guiManager);
            handleGameOfLifeKeyboardBindings(e, gameOfLifeComponent);
        });
    }

    private void registerMenuMouseButtons(Scene scene, GuiManager guiManager) {
        ZStackGuiComponent collapsableMenu = guiManager.getCollapsableMenu();
        StackPane stackPane = collapsableMenu.getPaneElement();

        stackPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            mouseOver.add(collapsableMenu);
            System.out.println("Mouse entered");
        });
        stackPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            mouseOver.remove(collapsableMenu);
            System.out.println("Mouse exited");
        });

    }

    private void handleMenuKeyboardBindings(KeyEvent keyEvent, GuiManager guiManager) {
        if (keyEvent.getCode() == ESCAPE_MENU_BIND) {
            guiManager.toggleRightMenu(guiManager.collapsableMenuToggled);
            guiManager.toggleCollapsableMenu(!guiManager.collapsableMenuToggled);
        }
    }

    private void registerGameOfLifeMouseButtons(final GameOfLifeGuiComponent gameOfLifeComponent) {
        Canvas gameOfLifeCanvas = gameOfLifeComponent.getCanvas();
        EventHandler<MouseEvent> paintHandler = generatePaintEventHandler();

        gameOfLifeCanvas.setOnMouseDragged(paintHandler);
        gameOfLifeCanvas.setOnMousePressed(paintHandler);

        gameOfLifeCanvas.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            mouseOver.add(gameOfLifeComponent);
            painter.setPaintMode(true, false);
            painter.setUnfocused(false);
            painter.reloadCursorGraphics(e);
        });
        gameOfLifeCanvas.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            mouseOver.remove(gameOfLifeComponent);
            painter.setPaintMode(false, false);
            painter.setUnfocused(true);
        });

        gameOfLifeCanvas.setOnScroll(painter::handleBrushResize);
    }

    private EventHandler<MouseEvent> generatePaintEventHandler() {
        return e -> painter.paint(e.getX(), e.getY(), e.isSecondaryButtonDown());
    }

    private void handleGameOfLifeKeyboardBindings(final KeyEvent keyEvent, final GameOfLifeGuiComponent gameOfLifeComponent) {
        if (!this.mouseOver.contains(gameOfLifeComponent)) return;

        if (keyEvent.getCode() == CLEAR_BOARD_BIND) {
            painter.attemptClearBoard();
        } else if (keyEvent.getCode() == TOGGLE_BRUSH_TYPE_BIND) {
            painter.attemptToggleBrushType();
        } else if (keyEvent.getCode() == TOGGLE_PAINT_MODE_BIND) {
            painter.togglePaintMode(true);
        }
    }

}
