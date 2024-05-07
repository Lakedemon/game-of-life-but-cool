package Game.ui;

import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.ZStackGuiComponent;

public class GuiHandler {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;

    public void initializeGuiComponents() {
        this.root = new ZStackGuiComponent();

        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);
        this.root.addChild(gameOfLifeGuiComponent);
    }

    public GameOfLifeGuiComponent getGameOfLifeGuiComponent() {
        return this.gameOfLifeGuiComponent;
    }

    public GuiComponent getRoot() {
        return root;
    }
}
