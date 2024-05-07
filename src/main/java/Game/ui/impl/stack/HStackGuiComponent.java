package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class HStackGuiComponent extends GuiComponent {

    private final HBox drawableElement;

    public HStackGuiComponent(int spacing, Color backgroundColor) {
        this.drawableElement = new HBox();
        this.drawableElement.setSpacing(spacing);
        this.drawableElement.setBackground(Background.fill(backgroundColor));
    }

    public HStackGuiComponent() {
        this.drawableElement = new HBox();
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

    public void alignToCenter() {
        this.drawableElement.setAlignment(Pos.CENTER);
    }
}
