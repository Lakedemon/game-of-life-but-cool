package Game.paint;

import Game.GameOfLife;
import Game.graphics.GraphicsHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Painter {

    private final GameOfLife gameOfLife;
    private final GraphicsHandler graphicsHandler;

    private final Brush brush;
    private final int DEFAULT_WIDTH = 20;
    private final BrushShape DEFAULT_BRUSH_SHAPE = BrushShape.CIRCLE;

    private boolean paintMode = true;

    public Painter(GameOfLife gameOfLife, GraphicsHandler graphicsHandler) {
        this.brush = new Brush(DEFAULT_WIDTH, DEFAULT_BRUSH_SHAPE);

        this.gameOfLife = gameOfLife;
        this.graphicsHandler = graphicsHandler;
    }

    public void handleBrushResize(ScrollEvent event) {
        if (!paintMode) return;

        this.brush.width += (int) event.getDeltaY() / 10;
        this.brush.clampWidth();

        graphicsHandler.getCursorGraphics().resizeCustomCursor(this.brush.width, event);

    }

    public void paint(MouseEvent e, Canvas canvas) {
        if (!paintMode) return;

        double x = (canvas.getTranslateX() + e.getX()) / graphicsHandler.getCellSize();
        double y = (canvas.getTranslateY() + e.getY()) / graphicsHandler.getCellSize();

        if (this.brush.shape.equals(BrushShape.CIRCLE))
            gameOfLife.setCircle((int) x, (int) y, brush.width / 2, !e.isSecondaryButtonDown());
        else if (this.brush.shape.equals(BrushShape.SQUARE))
            gameOfLife.setSquare((int) x, (int) y, brush.width, !e.isSecondaryButtonDown());
    }

    public void attemptClearBoard() {
        this.gameOfLife.clearBoard();
    }

    public void attemptToggleBrushType() {
        this.brush.shape = this.brush.shape.next();
        this.graphicsHandler.getCursorGraphics().setCursorShape(this.brush.shape);
    }

    public void togglePaintMode() {
        this.paintMode = !this.paintMode;
        this.graphicsHandler.getCursorGraphics().toggleCustomCursor();
    }

    public Brush getBrush() {
        return this.brush;
    }
}
