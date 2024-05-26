package Game.ui.impl;

import Game.ui.GuiComponent;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;


public class TextInputGuiComponent extends GuiComponent {

    private final javafx.scene.control.TextField drawableElement;
    private final int WIDTH, HEIGHT;

    private final SimpleStringProperty inputText;

    public TextInputGuiComponent(String placeholder, Color backgroundColor, int width, int height) {
        this.drawableElement = new TextField();
        this.inputText = new SimpleStringProperty(placeholder);

        this.drawableElement.setEditable(true);
        this.drawableElement.setMaxWidth(width);
        this.drawableElement.setMaxHeight(height);
        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);
        this.drawableElement.setBackground(Background.fill(backgroundColor));
        this.drawableElement.setStyle("-fx-text-fill: white");
        this.drawableElement.textProperty().addListener((observable, oldValue, newValue) -> {
            this.inputText.set(newValue);
        });

        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public String getText() {
        return this.inputText.get();
    }

    @Override
    public int getWidth() {
        return this.WIDTH;
    }

    @Override
    public int getHeight() {
        return this.HEIGHT;
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
