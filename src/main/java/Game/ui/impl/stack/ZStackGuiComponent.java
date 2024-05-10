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

    public ZStackGuiComponent(int width, int height) {
        this.drawableElement = new StackPane();

        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);

        this.drawableElement.setMaxWidth(width);
        this.drawableElement.setMaxHeight(height);
    }

    public StackPane getPaneElement() {
        return this.drawableElement;
    }

    @Override
    public void addChild(GuiComponent child) {
        super.addChild(child);
        this.drawableElement.getChildren().add(child.getDrawableElement());
    }

    @Override
    public boolean removeChild(GuiComponent child) {
        this.drawableElement.getChildren().remove(child.getDrawableElement());
        return super.removeChild(child);
    }

    public void setAlignment(Pos pos) {

        this.drawableElement.setAlignment(pos);
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
