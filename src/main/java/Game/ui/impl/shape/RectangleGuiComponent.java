package Game.ui.impl.shape;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleGuiComponent extends ClickableGuiComponent {

    private final Rectangle drawableElement;

    public RectangleGuiComponent(int width, int height, Color color) {
        this.drawableElement = new Rectangle();

        this.drawableElement.setWidth(width);
        this.drawableElement.setHeight(height);

        this.drawableElement.setFill(color);
        super.registerClicks();
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }

}
