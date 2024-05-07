package Game.ui.impl;

import Game.ui.GuiComponent;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ZStackGuiComponent extends GuiComponent {

    private final StackPane stackPane;

    public ZStackGuiComponent() {
        this.stackPane = new StackPane();
    }

    @Override
    public void addChild(GuiComponent child) {
        super.addChild(child);
        this.stackPane.getChildren().add(child.getDrawableElement());
    }

    @Override
    public Node getDrawableElement() {
        return this.stackPane;
    }
}
