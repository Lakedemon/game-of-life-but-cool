package Game.paint;

import Game.InputHandler;

public class Brush {

    public final int MIN_WIDTH = 2, MAX_WIDTH = 50;

    public int width;
    public InputHandler.CursorShape shape;

    public Brush(int width, InputHandler.CursorShape shape) {
        this.width = width;
        this.shape = shape;
    }

    public void clampWidth() {
        if (this.width < MIN_WIDTH) {
            this.width = MIN_WIDTH;
        } else if (this.width > MAX_WIDTH) {
            this.width = MAX_WIDTH;
        }
    }

    public boolean isCircular() {
        return this.shape.equals(InputHandler.CursorShape.CIRCLE);
    }

    public int getWidth() {
        return this.width;
    }

}