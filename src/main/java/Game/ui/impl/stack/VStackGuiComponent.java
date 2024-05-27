package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VStackGuiComponent extends GuiComponent {

    private final VBox drawableElement;

    private final int width, height;

    public VStackGuiComponent(int spacing, Color backgroundColor) {
        this.drawableElement = new VBox();
        this.width = 0;
        this.height = 0;
        this.drawableElement.setSpacing(spacing);
        this.drawableElement.setBackground(Background.fill(backgroundColor));
    }

    public VStackGuiComponent(int spacing, Color backgroundColor, int width, int height) {
        this.drawableElement = new VBox();
        this.width = width;
        this.height = height;
        this.drawableElement.setSpacing(spacing);
        this.drawableElement.setBackground(Background.fill(backgroundColor));

        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);

        this.drawableElement.setMaxHeight(height);
        this.drawableElement.setMaxWidth(width);
    }

    @Override
    public void addChild(GuiComponent child) {
        super.addChild(child);
        this.drawableElement.getChildren().add(child.getDrawableElement());
    }

    @Override
    public boolean removeChild(GuiComponent child) {
        super.removeChild(child);
        return this.drawableElement.getChildren().remove(child.getDrawableElement());
    }

    @Override
    public boolean hasChild(GuiComponent child) {
        return this.drawableElement.getChildren().contains(child.getDrawableElement());
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

    public void setAlignment(Pos alignment) {
        this.drawableElement.setAlignment(alignment);
    }
}
