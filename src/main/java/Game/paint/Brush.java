package Game.paint;

public class Brush {

    public final int MIN_WIDTH = 2, MAX_WIDTH = 50;

    public int width;
    public BrushShape shape;

    public Brush(int width, BrushShape shape) {
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
        return this.shape.equals(BrushShape.CIRCLE);
    }

    public int getWidth() {
        return this.width;
    }

}