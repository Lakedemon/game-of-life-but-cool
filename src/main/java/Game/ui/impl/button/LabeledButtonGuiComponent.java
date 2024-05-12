package Game.ui.impl.button;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class LabeledButtonGuiComponent extends ClickableGuiComponent {

    private final StackPane drawableElement;

    public LabeledButtonGuiComponent(String label, Font font, Color textColor, int width, int height, Color fillColor, Color strokeColor, int arc, int strokeWidth, ClickEvent eventHandler) {
        super(eventHandler);

        this.drawableElement = new StackPane();
        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);
        this.drawableElement.setMaxWidth(width);
        this.drawableElement.setMaxHeight(height);

        Rectangle background = new Rectangle(width, height, fillColor);
        background.setStrokeWidth(strokeWidth);
        background.setStroke(strokeColor);
        background.setArcHeight(arc);
        background.setArcWidth(arc);
        background.setWidth(width);
        background.setHeight(height);

        Label labelNode = new Label(label);
        labelNode.setFont(font);
        labelNode.setTextFill(textColor);

        this.drawableElement.getChildren().add(background);
        this.drawableElement.getChildren().add(labelNode);

        super.registerClicks();
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
