package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleComponent extends GuiComponent {

    private final Rectangle drawableElement;

    public RectangleComponent(int width, int height, Color color) {
        this.drawableElement = new Rectangle();

        this.drawableElement.setWidth(width);
        this.drawableElement.setHeight(height);

        this.drawableElement.setFill(color);
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
