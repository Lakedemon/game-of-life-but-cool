package Game.ui;

import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiHandler {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;

    public void initializeGuiComponents() {
        this.root = new HStackGuiComponent(2);

        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);
        Rectangle dummy = new Rectangle(600, 600);
        dummy.setFill(Color.BLUE);
        GuiComponent dummyComponent = new GuiComponent() {

            @Override
            public Node getDrawableElement() {
                return dummy;
            }
        };
        this.root.addChild(dummyComponent);

        this.root.addChild(gameOfLifeGuiComponent);
    }

    public GameOfLifeGuiComponent getGameOfLifeGuiComponent() {
        return this.gameOfLifeGuiComponent;
    }

    public GuiComponent getRoot() {
        return root;
    }
}
