package Game.ui;

import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class GuiManager {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;

    private final Color BG_COLOR = Color.grayRgb(30);

    public void initializeGuiComponents() {
        RectangleGuiComponent backgroundComponent = new RectangleGuiComponent(1500, 700, BG_COLOR);

        VStackGuiComponent mainPanel = new VStackGuiComponent(3, Color.grayRgb(30));
        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);

        Color settingsBG = BG_COLOR.deriveColor(0, 0, 1.3, 1);
        HStackGuiComponent settingsComponent = new HStackGuiComponent(60, settingsBG, 600, 60, 30);
        settingsComponent.setAlignment(Pos.CENTER_LEFT);

        VStackGuiComponent saveGridComponent = new VStackGuiComponent(4, settingsBG);
        saveGridComponent.setAlignment(Pos.CENTER);
        saveGridComponent.addChild(new RectangleGuiComponent(120, 20, Color.BLUE));
        saveGridComponent.addChild(new RectangleGuiComponent(120, 20, Color.BLUE));
        settingsComponent.addChild(saveGridComponent);

        VStackGuiComponent volumesComponent = new VStackGuiComponent(4, settingsBG);
        volumesComponent.setAlignment(Pos.CENTER);
        volumesComponent.addChild(new RectangleGuiComponent(260, 20, Color.GREEN));
        volumesComponent.addChild(new RectangleGuiComponent(260, 20, Color.GREEN));
        settingsComponent.addChild(volumesComponent);

        RectangleGuiComponent exitComponent = new RectangleGuiComponent(44, 44, Color.RED);
        settingsComponent.addChild(exitComponent);

        mainPanel.addChild(gameOfLifeGuiComponent);
        mainPanel.addChild(settingsComponent);
        mainPanel.setAlignment(Pos.CENTER);

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
        return this.gameOfLifeGuiComponent.getCanvas();
    }
}
