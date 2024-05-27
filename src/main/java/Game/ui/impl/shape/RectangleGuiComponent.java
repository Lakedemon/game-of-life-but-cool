package Game.ui.impl.shape;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleGuiComponent extends ClickableGuiComponent {

    private final Rectangle drawableElement;
    private final int width, height;

    public RectangleGuiComponent(int width, int height, Color color, ClickEvent eventHandler) {
        super(eventHandler);
        this.drawableElement = new Rectangle();
        this.width = width;
        this.height = height;

        this.drawableElement.setWidth(width);
        this.drawableElement.setHeight(height);

        this.drawableElement.setFill(color);
        super.registerClicks();
    }

    public RectangleGuiComponent(int width, int height, Color color) {
        super(e -> {});
        this.drawableElement = new Rectangle();

        this.width = width;
        this.height = height;

        this.drawableElement.setWidth(width);
        this.drawableElement.setHeight(height);

        this.drawableElement.setFill(color);
        super.registerClicks();
    }

    public void setRadius(int radius) {
        this.drawableElement.setArcHeight(radius);
        this.drawableElement.setArcWidth(radius);
    }

    public void setStroke(int width, Color color) {
        this.drawableElement.setStrokeWidth(width);
        this.drawableElement.setStroke(color);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }

}
