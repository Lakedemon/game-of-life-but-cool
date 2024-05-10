package Game.ui;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class GuiComponent {

    private final UUID UNIQUE_ID;

    protected final List<GuiComponent> children;

    public GuiComponent() {
        this.UNIQUE_ID = UUID.randomUUID();
        children = new ArrayList<>();
    }

    public void addChild(GuiComponent child) {
        children.add(child);
    }

    public boolean removeChild(GuiComponent child) {
        return children.remove(child);
    }

    public abstract Node getDrawableElement();

    @Override
    public int hashCode() {
        return this.UNIQUE_ID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GuiComponent && this.UNIQUE_ID.equals(((GuiComponent) obj).UNIQUE_ID);
    }

}
