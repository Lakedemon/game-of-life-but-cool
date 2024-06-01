package Game.paint;

import Game.GameOfLife;
import Game.Main;
import Game.structures.Structure;
import Game.ui.cursor.CursorGraphicsHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Painter {

    private static final int DEFAULT_WIDTH = 20;
    private static final BrushShape DEFAULT_BRUSH_SHAPE = BrushShape.CIRCLE;

    private final GameOfLife gameOfLife;
    private final CursorGraphicsHandler cursorGraphicsHandler;
    private final Brush brush;

    private boolean paintMode = true;
    private boolean manuallyDisabled = false;
    private boolean unfocused = false;

    private int manualSize;
    private BrushShape manualShape;

    private Structure structureSelected = null;

    public Painter(GameOfLife gameOfLife, CursorGraphicsHandler cursorGraphicsHandler) {
        this.brush = new Brush(DEFAULT_WIDTH, DEFAULT_BRUSH_SHAPE);

        this.gameOfLife = gameOfLife;
        this.cursorGraphicsHandler = cursorGraphicsHandler;
        this.manualSize = DEFAULT_WIDTH;
        this.manualShape = DEFAULT_BRUSH_SHAPE;
    }

    public void handleBrushResize(ScrollEvent event) {
        if (!paintMode) return;

        this.brush.width += (int) event.getDeltaY() / 10;
        this.brush.clampWidth();

        this.manualSize = this.brush.width;

        cursorGraphicsHandler.resizeCustomCursor(this.brush.width, event);

    }

    public void handleStructureSelect(Structure structure) {
        this.structureSelected = structure;

        if (structure != null) {
            int newSize = structure.getGrid().length;
            cursorGraphicsHandler.setCursorShape(BrushShape.SQUARE);
            cursorGraphicsHandler.setSize(newSize);
        } else {
            cursorGraphicsHandler.setCursorShape(this.manualShape);
            cursorGraphicsHandler.setSize(manualSize);
        }

    }

    public void paint(double x, double y, boolean remove) {
        if (!paintMode) return;

        x /= Main.CELL_SIZE;
        y /= Main.CELL_SIZE;

        if (structureSelected != null) {
            // Paint structure
            gameOfLife.placeStructure(this.structureSelected, (int) x, (int) y, remove);
            return;
        }

        if (this.brush.shape.equals(BrushShape.CIRCLE))
            gameOfLife.setCircle((int) x, (int) y, brush.width / 2, !remove);
        else if (this.brush.shape.equals(BrushShape.SQUARE))
            gameOfLife.setSquare((int) x, (int) y, brush.width, !remove);
    }

    public void attemptClearBoard() {
        this.gameOfLife.clearBoard();
    }

    public void attemptResetBoard(){
        this.gameOfLife.ranFill();
    }

    public void attemptToggleBrushType() {
        this.brush.shape = this.brush.shape.next();
        this.cursorGraphicsHandler.setCursorShape(this.brush.shape);
        this.manualShape = this.brush.shape;
    }

    public void togglePaintMode(boolean manually) {
        setPaintMode(!paintMode, manually);
    }

    public void setPaintMode(boolean paintMode, boolean manually) {
        if (manuallyDisabled && unfocused && !manually) {
            return;
        }

        if (!paintMode && manually) {
            manuallyDisabled = true;
        } else if (paintMode && manually) {
            manuallyDisabled = false;
        }

        this.paintMode = paintMode;
        this.cursorGraphicsHandler.setCustomCursorStatus(paintMode);
    }

    public void reloadCursorGraphics(MouseEvent e) {
        this.cursorGraphicsHandler.reloadCursorPosition((int) e.getX(), (int) e.getY());
    }

    public void setUnfocused(boolean unfocused) {
        this.unfocused = unfocused;
    }

    public Brush getBrush() {
        return this.brush;
    }
}
