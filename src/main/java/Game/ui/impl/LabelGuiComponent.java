package Game.ui.impl;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelGuiComponent extends GuiComponent {

    private final Label drawableElement;

    public LabelGuiComponent(String label, int height, int fontSize, String fontFamily, Color color) {
        Font font = Font.font(fontFamily, FontWeight.LIGHT, fontSize);
        this.drawableElement = new Label(label);
        this.drawableElement.setFont(font);
        this.drawableElement.setTextFill(color);
        this.drawableElement.setMinHeight(height);
        this.drawableElement.setMaxHeight(height);

        drawableElement.setMinWidth(Region.USE_PREF_SIZE);

    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
