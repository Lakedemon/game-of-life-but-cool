package Game.paint;

public enum BrushShape {
    SQUARE(false),
    CIRCLE(true);

    public BrushShape next() {
        return BrushShape.values()[(this.ordinal() + 1) % BrushShape.values().length];
    }

    public boolean isCircular() {
        return this.circular;
    }

    private final boolean circular;

    BrushShape(boolean circular) {
        this.circular = circular;
    }
}