package Game.paint;

import Game.GameOfLife;
import Game.Main;
import Game.ui.cursor.CursorGraphicsHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Painter {

    private static final int DEFAULT_WIDTH = 20;
    private static final BrushShape DEFAULT_BRUSH_SHAPE = BrushShape.CIRCLE;

    private final GameOfLife gameOfLife;
    private final CursorGraphicsHandler cursorGraphicsHandler;
    private final Brush brush;

    private boolean paintMode = true;

    public Painter(GameOfLife gameOfLife, CursorGraphicsHandler cursorGraphicsHandler) {
        this.brush = new Brush(DEFAULT_WIDTH, DEFAULT_BRUSH_SHAPE);

        this.gameOfLife = gameOfLife;
        this.cursorGraphicsHandler = cursorGraphicsHandler;
    }

    public void handleBrushResize(ScrollEvent event) {
        if (!paintMode) return;

        this.brush.width += (int) event.getDeltaY() / 10;
        this.brush.clampWidth();

        cursorGraphicsHandler.resizeCustomCursor(this.brush.width, event);

    }

    public void paint(double x, double y, boolean remove) {
        if (!paintMode) return;

        x /= Main.CELL_SIZE;
        y /= Main.CELL_SIZE;

        if (this.brush.shape.equals(BrushShape.CIRCLE))
            gameOfLife.setCircle((int) x, (int) y, brush.width / 2, !remove);
        else if (this.brush.shape.equals(BrushShape.SQUARE))
            gameOfLife.setSquare((int) x, (int) y, brush.width, !remove);
    }

    public void attemptClearBoard() {
        this.gameOfLife.clearBoard();
    }

    public void attemptToggleBrushType() {
        this.brush.shape = this.brush.shape.next();
        this.cursorGraphicsHandler.setCursorShape(this.brush.shape);
    }

    public void togglePaintMode() {
        this.paintMode = !this.paintMode;
        this.cursorGraphicsHandler.toggleCustomCursor();
    }

    public Brush getBrush() {
        return this.brush;
    }
}
