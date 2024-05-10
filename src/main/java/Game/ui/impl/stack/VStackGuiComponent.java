package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VStackGuiComponent extends GuiComponent {

    private final VBox drawableElement;

    public VStackGuiComponent(int spacing, Color backgroundColor) {
        this.drawableElement = new VBox();
        this.drawableElement.setSpacing(spacing);
        this.drawableElement.setBackground(Background.fill(backgroundColor));
    }

    public VStackGuiComponent(int spacing, Color backgroundColor, int width, int height) {
        this.drawableElement = new VBox();
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
    public Node getDrawableElement() {
        return this.drawableElement;
    }

    public void setAlignment(Pos alignment) {
        this.drawableElement.setAlignment(alignment);
    }
}
