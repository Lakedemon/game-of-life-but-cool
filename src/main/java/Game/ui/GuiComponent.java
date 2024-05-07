package Game.ui;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiComponent {

    protected final List<GuiComponent> children;

    public GuiComponent() {
        children = new ArrayList<>();
    }

    public void addChild(GuiComponent child) {
        children.add(child);
    }

    public abstract Node getDrawableElement();

}
