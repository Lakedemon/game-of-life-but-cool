package Game.gameGraphics;

import static Game.Main.CELL_SIZE;

public class GraphicsHandler {

    private final CursorGraphicsHandler cursorGraphics;

    public GraphicsHandler() {
        this.cursorGraphics = new CursorGraphicsHandler();
    }

    public CursorGraphicsHandler getCursorGraphics() {
        return cursorGraphics;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }
}
