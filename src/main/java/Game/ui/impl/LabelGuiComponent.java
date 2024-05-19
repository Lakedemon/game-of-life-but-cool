package Game.ui.impl;

import Game.ui.GuiComponent;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelGuiComponent extends GuiComponent {

    private final Label drawableElement;

    private final int width, height;

    public LabelGuiComponent(String label, int fontSize, String fontFamily, Color color) {
        Font font = Font.font(fontFamily, FontWeight.LIGHT, fontSize);
        this.drawableElement = new Label(label);

        this.drawableElement.setFont(font);
        this.drawableElement.setTextFill(color);

        drawableElement.setMinHeight(Region.USE_PREF_SIZE);
        drawableElement.setMinWidth(Region.USE_PREF_SIZE);

        this.width = (int) drawableElement.getMinWidth();
        this.height = (int) drawableElement.getMinHeight();
    }

    public LabelGuiComponent(StringProperty stringProperty, int fontSize, String fontFamily, Color color) {
        Font font = Font.font(fontFamily, FontWeight.LIGHT, fontSize);
        this.drawableElement = new Label(stringProperty.get());

        stringProperty.addListener((observable, oldValue, newValue) -> {
            drawableElement.setText(newValue);
        });

        this.drawableElement.setFont(font);
        this.drawableElement.setTextFill(color);

        drawableElement.setMinHeight(Region.USE_PREF_SIZE);
        drawableElement.setMinWidth(Region.USE_PREF_SIZE);

        this.width = (int) drawableElement.getMinWidth();
        this.height = (int) drawableElement.getMinHeight();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
