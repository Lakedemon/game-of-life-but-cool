package Game.graphics;

import Game.paint.Brush;
import Game.paint.BrushShape;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CursorGraphicsHandler {

    private DrawingCursor drawingCursor;
    private boolean cursorToggled;

    public void initCustomCursor(Scene scene, Pane root, Brush brush) {
        this.drawingCursor = new DrawingCursor(scene, root, brush.getWidth(), brush.getWidth());

        if (brush.isCircular()) {
            this.drawingCursor.makeCircle();
        }
        this.drawingCursor.setWidth(brush.getWidth());
        this.drawingCursor.setColor(Color.AQUA);

        this.drawingCursor.activate();
        this.cursorToggled = true;
    }

    public void resizeCustomCursor(int newWidth, ScrollEvent event) {
        this.drawingCursor.setWidth(newWidth);
        this.drawingCursor.setHotSpotX(newWidth);
        this.drawingCursor.setHotSpotY(newWidth);

        this.drawingCursor.reloadPositionFromScroll(event);
    }

    public void toggleCustomCursor() {
        if (cursorToggled) {
            this.drawingCursor.unRegister();
            cursorToggled = false;
        } else {
            this.drawingCursor.reRegister();
            cursorToggled = true;
        }
    }

    public void setCursorShape(BrushShape cursorShape) {
        int currentWidth = this.drawingCursor.getWidth();
        Color currentColor = this.drawingCursor.getColor();

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

    }

}
