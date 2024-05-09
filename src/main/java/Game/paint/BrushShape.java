package Game.paint;

public enum BrushShape {
    SQUARE(),
    CIRCLE();

    public BrushShape next() {
        return BrushShape.values()[(this.ordinal() + 1) % BrushShape.values().length];
    }

}