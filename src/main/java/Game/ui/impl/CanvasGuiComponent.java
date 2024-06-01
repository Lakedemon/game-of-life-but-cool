package Game.ui.impl;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class CanvasGuiComponent extends GuiComponent {

    private final Canvas drawableElement;
    private final int WIDTH, HEIGHT;

    public CanvasGuiComponent(int width, int height) {
        drawableElement = new Canvas(width, height);
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void setFill(Color color) {
        this.drawableElement.getGraphicsContext2D().setFill(color);
    }

    public void drawRect(int x, int y, int width, int height) {
        drawableElement.getGraphicsContext2D().fillRect(x, y, width, height);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public Node getDrawableElement() {
        return drawableElement;
    }
}
