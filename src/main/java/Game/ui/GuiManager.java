package Game.ui;

import Game.ui.cursor.CursorGraphicsHandler;
import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.LabelGuiComponent;
import Game.ui.impl.LabeledButtonGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GuiManager {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;
    private final CursorGraphicsHandler cursorGraphicsHandler;

    private ZStackGuiComponent collapsableMenu;
    private boolean collapsableMenuToggled = false;

    private final Color BG_COLOR = Color.grayRgb(30);
    private final Color ACCENT = Color.web("#4d62ff", 0.7);

    public void initializeGuiComponents() {
        this.collapsableMenu = initializeCollapsableMenu();

        RectangleGuiComponent backgroundComponent = new RectangleGuiComponent(1150, 700, BG_COLOR);

        HStackGuiComponent mainPanel = new HStackGuiComponent(5, BG_COLOR, 0);
        VStackGuiComponent gamePanel = new VStackGuiComponent(3, BG_COLOR);
        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);

        Color settingsBG = BG_COLOR.deriveColor(0, 0, 1.3, 1);
        HStackGuiComponent settingsComponent = new HStackGuiComponent(60, settingsBG, 600, 60, 30);
        settingsComponent.setAlignment(Pos.CENTER_LEFT);

        VStackGuiComponent gridOperationsPanel = new VStackGuiComponent(4, settingsBG);
        gridOperationsPanel.setAlignment(Pos.CENTER);

        Font gridOperationsFont = Font.font("Helvetica", FontWeight.NORMAL, 18);
        LabeledButtonGuiComponent saveGridButton = new LabeledButtonGuiComponent("Save Grid", gridOperationsFont, Color.WHITE, 120, 25,
                settingsBG, ACCENT, 5, 1, e -> System.out.println("Saving grid"));
        LabeledButtonGuiComponent loadGridButton = new LabeledButtonGuiComponent("Load Grid", gridOperationsFont, Color.WHITE, 120, 25,
                settingsBG, ACCENT, 5, 1, e -> System.out.println("Loading grid"));

        gridOperationsPanel.addChild(saveGridButton);
        gridOperationsPanel.addChild(loadGridButton);

        settingsComponent.addChild(gridOperationsPanel);

        VStackGuiComponent volumesComponent = new VStackGuiComponent(4, settingsBG);
        volumesComponent.setAlignment(Pos.CENTER);

        LabelGuiComponent volumeComponent1 = new LabelGuiComponent("SFX Volume:   ------o---         ", 20, 18, "Helvetica", Color.WHITE);
        LabelGuiComponent volumeComponent2 = new LabelGuiComponent("Music Volume: ------o---         ", 20, 18, "Helvetica", Color.WHITE);
        volumesComponent.addChild(volumeComponent1);
        volumesComponent.addChild(volumeComponent2);

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
            ((StackPane)this.root.getDrawableElement()).setAlignment(Pos.CENTER_LEFT);
            this.root.addChild(this.collapsableMenu);
            this.collapsableMenuToggled = true;
        } else if (!toggled && collapsableMenuToggled) {
            ((StackPane)this.root.getDrawableElement()).setAlignment(Pos.CENTER);
            this.root.removeChild(this.collapsableMenu);
            this.collapsableMenuToggled = false;
        }
    }

    private ZStackGuiComponent initializeCollapsableMenu() {
        Color bgColor = BG_COLOR.deriveColor(0, 0, 1.4, 0.95);
        ZStackGuiComponent menu = new ZStackGuiComponent(700, 450);
        RectangleGuiComponent background = new RectangleGuiComponent(663, 663, bgColor);
        background.setStroke(2, ACCENT.deriveColor(0, 1, 1, 0.8));

        menu.addChild(background);

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
