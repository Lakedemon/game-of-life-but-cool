package Game.ui;

import Game.ui.cursor.CursorGraphicsHandler;
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
    private final CursorGraphicsHandler cursorGraphicsHandler;

    private ZStackGuiComponent collapsableMenu;
    private boolean collapsableMenuToggled = false;

    private final Color BG_COLOR = Color.grayRgb(30);

    public void initializeGuiComponents() {
        this.collapsableMenu = initializeCollapsableMenu();

        RectangleGuiComponent backgroundComponent = new RectangleGuiComponent(1150, 700, BG_COLOR);

        HStackGuiComponent mainPanel = new HStackGuiComponent(5, BG_COLOR, 0);
        VStackGuiComponent gamePanel = new VStackGuiComponent(3, BG_COLOR);
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

        gamePanel.addChild(gameOfLifeGuiComponent);
        gamePanel.addChild(settingsComponent);
        gamePanel.setAlignment(Pos.CENTER_LEFT);

        VStackGuiComponent settingsMenuButtonSide = new VStackGuiComponent(0, BG_COLOR, 50, 663);
        RectangleGuiComponent settingsMenuButton = new RectangleGuiComponent(50, 50, Color.DARKCYAN,
                e -> {
                    System.out.println("Toggling menu");
                    this.toggleCollapsableMenu(true);
                });

        settingsMenuButtonSide.addChild(settingsMenuButton);
        mainPanel.addChild(settingsMenuButtonSide);
        mainPanel.addChild(gamePanel);
        mainPanel.addChild(new RectangleGuiComponent(450, 663, BG_COLOR.deriveColor(0, 0, 0.9, 1)));

        mainPanel.setAlignment(Pos.CENTER);

        this.root = new ZStackGuiComponent();
        this.root.addChild(backgroundComponent);
        this.root.addChild(mainPanel);
    }

    public ZStackGuiComponent getCollapsableMenu() {
        return collapsableMenu;
    }

    public void toggleCollapsableMenu(boolean toggled) {
        if (toggled && !collapsableMenuToggled) {
            this.root.addChild(this.collapsableMenu);
            this.collapsableMenuToggled = true;
        } else if (!toggled && collapsableMenuToggled) {
            this.root.removeChild(this.collapsableMenu);
            this.collapsableMenuToggled = false;
        }
    }

    private ZStackGuiComponent initializeCollapsableMenu() {
        Color bgColor = BG_COLOR.deriveColor(0, 0, 2, 0.95);
        ZStackGuiComponent menu = new ZStackGuiComponent(663, 663);
        RectangleGuiComponent background = new RectangleGuiComponent(663, 663, bgColor);
        background.setStroke(2, Color.WHITE.deriveColor(0, 0, 1, 0.95));

        menu.addChild(background);
        menu.setAlignment(Pos.CENTER_LEFT);

        return menu;
    }

    public GameOfLifeGuiComponent getGameOfLifeGuiComponent() {
        return this.gameOfLifeGuiComponent;
    }

    public GuiComponent getRoot() {
        return root;
    }

    public GuiManager(CursorGraphicsHandler cursorGraphicsHandler) {
        this.cursorGraphicsHandler = cursorGraphicsHandler;
    }

    public Canvas getGameOfLifeCanvas() {
        return this.gameOfLifeGuiComponent.getCanvas();
    }
}
