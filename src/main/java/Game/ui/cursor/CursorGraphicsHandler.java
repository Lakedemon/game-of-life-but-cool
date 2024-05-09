package Game.ui.cursor;

import Game.paint.Brush;
import Game.paint.BrushShape;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CursorGraphicsHandler {

    private DrawingCursor drawingCursor;
    private boolean cursorToggled;

    private final Color cursorColor = Color.DARKRED;
    private static final double STROKE_WIDTH = 3;

    public void initCustomCursor(Canvas gameOfLifeCanvas, Pane root, Brush brush) {
        this.drawingCursor = new DrawingCursor(gameOfLifeCanvas, root, brush.getWidth(), brush.getWidth());

        if (brush.isCircular()) {
            this.drawingCursor.makeCircle();
        }
        this.drawingCursor.setWidth(brush.getWidth());
        this.drawingCursor.setColor(cursorColor);
        this.drawingCursor.setStrokeWidth(STROKE_WIDTH);

        this.drawingCursor.activate(false);
        this.cursorToggled = true;
    }

    public void resizeCustomCursor(int newEdgeLength, ScrollEvent event) {
        this.drawingCursor.setWidth(newEdgeLength);
        this.drawingCursor.setHotSpotX(newEdgeLength);
        this.drawingCursor.setHotSpotY(newEdgeLength);

        this.drawingCursor.reloadPositionFromScroll(event);
    }

    public void setCustomCursorStatus(boolean on) {
        if (!on && cursorToggled) {
            this.drawingCursor.unRegister();
            cursorToggled = false;
        } else if (on && !cursorToggled) {
            this.drawingCursor.reRegister();
            cursorToggled = true;
        }
    }

    public void setCursorShape(BrushShape cursorShape) {
        int currentWidth = this.drawingCursor.getWidth();
        Color currentColor = this.drawingCursor.getColor();
        double currentStrokeWidth = this.drawingCursor.getStrokeWidth();

        switch (cursorShape) {
            case CIRCLE:
                this.drawingCursor.makeCircle();
                break;
            case SQUARE:
                this.drawingCursor.makeSquare();
                break;
        }

        this.drawingCursor.setWidth(currentWidth);
        this.drawingCursor.setColor(currentColor);
        this.drawingCursor.setStrokeWidth(currentStrokeWidth);

    }

}
