package Game.ui.impl.stack;

import Game.ui.GuiComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ZStackGuiComponent extends GuiComponent {

    private final StackPane drawableElement;

    public ZStackGuiComponent() {
        this.drawableElement = new StackPane();
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
}
