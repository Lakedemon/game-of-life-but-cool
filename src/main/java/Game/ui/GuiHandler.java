package Game.ui;

import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.RectangleComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiHandler {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;

    public void initializeGuiComponents() {
        RectangleComponent backgroundComponent = new RectangleComponent(1500, 800, Color.grayRgb(30));

        HStackGuiComponent mainPanel = new HStackGuiComponent(10, Color.grayRgb(30));
        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);
        RectangleComponent dummyComponent = new RectangleComponent(600, 600, Color.BLUE);

        mainPanel.addChild(dummyComponent);
        mainPanel.addChild(gameOfLifeGuiComponent);
        mainPanel.alignToCenter();

        this.root = new ZStackGuiComponent();
        this.root.addChild(backgroundComponent);
        this.root.addChild(mainPanel);
    }

    public GameOfLifeGuiComponent getGameOfLifeGuiComponent() {
        return this.gameOfLifeGuiComponent;
    }

    public GuiComponent getRoot() {
        return root;
    }

    public Canvas getGameOfLifeCanvas() {
        return (Canvas) this.gameOfLifeGuiComponent.getDrawableElement();
    }
}
