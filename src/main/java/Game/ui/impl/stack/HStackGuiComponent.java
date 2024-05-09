package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class HStackGuiComponent extends GuiComponent {

    private final HBox drawableElement;
    private final Region offsetElement;
    private final int spacing;

    public HStackGuiComponent(int spacing, Color backgroundColor, int width, int height, int offset) {
        this.drawableElement = new HBox();
        this.offsetElement = new Region();
        this.spacing = spacing;

        this.drawableElement.setBackground(Background.fill(backgroundColor));

        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);
        this.drawableElement.setMaxWidth(width);
        this.drawableElement.setMaxHeight(height);

        HBox.setHgrow(offsetElement, Priority.ALWAYS);
        offsetElement.setMinWidth(offset);
        offsetElement.setMaxWidth(offset);

        this.drawableElement.getChildren().add(offsetElement);
    }

    @Override
    public void addChild(GuiComponent child) {
        super.addChild(child);
        this.drawableElement.getChildren().add(child.getDrawableElement());

        Region spacing = new Region();
        HBox.setHgrow(offsetElement, Priority.SOMETIMES);
        spacing.setMinWidth(this.spacing);
        spacing.setMaxWidth(this.spacing);

        this.drawableElement.getChildren().add(spacing);
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }

    public void setAlignment(Pos alignment) {
        this.drawableElement.setAlignment(alignment);
    }
}
