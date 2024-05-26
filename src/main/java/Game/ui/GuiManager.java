package Game.ui;

import static Game.file.StaticFileHandler.*;

import Game.rules.RuleBook;
import Game.rules.RulePane;
import Game.ui.animations.Animation;
import Game.ui.animations.impl.SlideAnimation;
import Game.ui.animations.impl.SlideOnScreenAnimation;
import Game.ui.clicking.ClickEvent;
import Game.ui.impl.GameOfLifeGuiComponent;
import Game.ui.impl.StructureSelectGuiComponent;
import Game.ui.impl.button.ImagedButtonGuiComponent;
import Game.ui.impl.LabelGuiComponent;
import Game.ui.impl.button.LabeledButtonGuiComponent;
import Game.ui.impl.rule.RulesGuiComponent;
import Game.ui.impl.slider.SliderGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Optional;

public class GuiManager {

    private GuiComponent root;
    private GameOfLifeGuiComponent gameOfLifeGuiComponent;

    private ZStackGuiComponent collapsableMenu;
    private RulesGuiComponent rulePane;
    public boolean collapsableMenuToggled = false;
    private SlideAnimation leftSlideAnimation;
    private SlideAnimation rightSlideAnimation;
    private SlideOnScreenAnimation golSlideAnimation;
    private SimpleBooleanProperty animationsRunningProperty;

    private HStackGuiComponent mainPanel;
    private ZStackGuiComponent rightComponent;
    private VStackGuiComponent rightMenu;
    private VStackGuiComponent gamePanel;

    public static final int STAGE_WIDTH = 1150;
    public static final int STAGE_HEIGHT = 700;

    public static final Color BG_COLOR = Color.grayRgb(30);
    public static final Color SETTINGS_BG = BG_COLOR.deriveColor(0, 0, 1.3, 1);
    public static final Color ACCENT = Color.web("#4d62ff", 0.7);

    public void initializeGuiComponents() {
        this.collapsableMenu = initializeCollapsableMenu();
        RectangleGuiComponent backgroundComponent = new RectangleGuiComponent(1150, 700, BG_COLOR);

        this.mainPanel = new HStackGuiComponent(5, BG_COLOR, 0);
        this.gamePanel = new VStackGuiComponent(3, BG_COLOR);

        this.rulePane = new RulesGuiComponent(new RuleBook(), 400, 400, 5);
        VStackGuiComponent rulePanel = new VStackGuiComponent(5, BG_COLOR);
        rulePanel.setAlignment(Pos.TOP_CENTER);
        rulePanel.addChild(rulePane);
        collapsableMenu.addChild(rulePanel);

        this.gameOfLifeGuiComponent = new GameOfLifeGuiComponent(300);
        gamePanel.addChild(gameOfLifeGuiComponent);

        HStackGuiComponent settingsComponent = this.initializeSettingsComponent(SETTINGS_BG);

        gamePanel.addChild(settingsComponent);
        gamePanel.setAlignment(Pos.CENTER_LEFT);

        VStackGuiComponent settingsMenuButtonSide = new VStackGuiComponent(0, BG_COLOR, 50, 663);

        Optional<ImagedButtonGuiComponent> optionalSettingsMenuButton = initializeSettingsMenuButton();

        if (optionalSettingsMenuButton.isPresent()) {
            settingsMenuButtonSide.addChild(optionalSettingsMenuButton.get());
            this.mainPanel.addChild(settingsMenuButtonSide);
        } else {
            settingsMenuButtonSide.addChild(initializeBackupSettingsMenuButton());
            this.mainPanel.addChild(settingsMenuButtonSide);
        }

        this.mainPanel.addChild(gamePanel);

        Color rightBgColor = BG_COLOR.deriveColor(0, 0, 0.9, 1);
        this.rightComponent = new ZStackGuiComponent(450, 663);
        RectangleGuiComponent rightBackground = new RectangleGuiComponent(450, 663, rightBgColor);
        rightBackground.setStroke(2, ACCENT);
        rightBackground.setRadius(10);
        this.rightMenu = initializeRightMenu(rightBgColor);

        rightComponent.addChild(rightBackground);
        rightComponent.addChild(rightMenu);
        this.rightMenu.setAlignment(Pos.TOP_CENTER);

        this.mainPanel.addChild(rightComponent);
        this.mainPanel.setAlignment(Pos.CENTER);

        this.root = new ZStackGuiComponent();
        this.root.addChild(backgroundComponent);
        this.root.addChild(mainPanel);
    }

    public void initializeAnimations() {
        this.animationsRunningProperty = new SimpleBooleanProperty(false);
        this.leftSlideAnimation = new SlideAnimation(SlideAnimation.Direction.LEFT, collapsableMenu, 1000, Animation.Easing.CUBIC_EASE_OUT);
        this.rightSlideAnimation = new SlideAnimation(SlideAnimation.Direction.RIGHT, this.rightComponent, 1000, Animation.Easing.CUBIC_EASE_OUT);
        this.golSlideAnimation = new SlideOnScreenAnimation(469, 0, this.gamePanel, 1000, Animation.Easing.CUBIC_EASE_OUT);
    }

    private VStackGuiComponent initializeRightMenu(Color bgColor) {
        VStackGuiComponent root = new VStackGuiComponent(10, bgColor, 300, 550);
        LabelGuiComponent title = new LabelGuiComponent("Structures", 50, "Helvetica", Color.WHITE);

        StructureSelectGuiComponent structureSelectGuiComponent = new StructureSelectGuiComponent(300, 450, bgColor, this);
        root.setAlignment(Pos.TOP_CENTER);

        root.addChild(title);
        root.addChild(structureSelectGuiComponent);
        root.setAlignment(Pos.TOP_CENTER);
        return root;
    }

    public RulesGuiComponent getRulePane(){
        return this.rulePane;
    }

    public VStackGuiComponent getRightMenu() {
        return this.rightMenu;
    }

    private RectangleGuiComponent initializeBackupSettingsMenuButton() {
        return new RectangleGuiComponent(50, 50, Color.DARKCYAN,
            e -> {
                System.out.println("Toggling menu");
                this.toggleCollapsableMenu(true);
            });
    }

    private void toggleRightMenu(boolean status) {
        if (this.rightSlideAnimation.isActive())
            return;

        if (status) {
            if (!this.mainPanel.hasChild(this.rightComponent)) {
                this.mainPanel.addChild(this.rightComponent);
            }
            this.rightSlideAnimation.perform(true, this.mainPanel);
            return;
        }

        this.rightSlideAnimation.perform(false, this.mainPanel);
    }

    private Optional<ImagedButtonGuiComponent> initializeSettingsMenuButton() {
        ClickEvent eventHandler = e -> {
             System.out.println("Toggling menu");
             this.switchPerspective(this.collapsableMenuToggled);
        };

        Optional<InputStream> optionalImageInput = getImageInputStream(SETTINGS_MENU_IMG_RESOURCE_PATH);
        if (optionalImageInput.isEmpty()) {
            System.out.println("ERROR: Failed to load settings menu button.");
            return Optional.empty();
        }

        ImagedButtonGuiComponent component = ImagedButtonGuiComponent.fromInputStream(
                optionalImageInput.get(), 45, 45, true, BG_COLOR, ACCENT, 1, 10, eventHandler
        );

        component.maskBlackPixels(ACCENT);

        return Optional.of(component);
    }

    public ZStackGuiComponent getCollapsableMenu() {
        return collapsableMenu;
    }

    public HStackGuiComponent initializeSettingsComponent(Color settingsBG) {
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

        HStackGuiComponent volumesComponent = initializeVolumesSection();
        settingsComponent.addChild(volumesComponent);

        RectangleGuiComponent exitComponent = new RectangleGuiComponent(44, 44, Color.RED, (e) -> System.exit(0));
        settingsComponent.addChild(exitComponent);

        return settingsComponent;
    }

    private HStackGuiComponent initializeVolumesSection() {
        HStackGuiComponent volumesSection = new HStackGuiComponent(10, SETTINGS_BG, 5);
        VStackGuiComponent labels = new VStackGuiComponent(0, SETTINGS_BG);
        VStackGuiComponent sliders = new VStackGuiComponent(14, SETTINGS_BG);
        sliders.setAlignment(Pos.CENTER_LEFT);
        labels.setAlignment(Pos.CENTER_RIGHT);


        LabelGuiComponent sfxVolumeLabel = new LabelGuiComponent("SFX Volume:", 20, "Helvetica", Color.WHITE);
        SliderGuiComponent sfxVolumeSlider = new SliderGuiComponent(0, 100, 75, 100, (o,  n) -> {
            System.out.println("Set sfx volume to " + n);
        });

        LabelGuiComponent musicVolumeLabel = new LabelGuiComponent("Music Volume:", 20, "Helvetica", Color.WHITE);
        SliderGuiComponent musicVolumeSlider = new SliderGuiComponent(0, 100, 75, 100, (o,  n) -> {
            System.out.println("Set music volume to " + n);
        });

        labels.addChild(sfxVolumeLabel);
        labels.addChild(musicVolumeLabel);

        sliders.addChild(sfxVolumeSlider);
        sliders.addChild(musicVolumeSlider);

        volumesSection.addChild(labels);
        volumesSection.addChild(sliders);

        return volumesSection;
    }

    private void toggleCollapsableMenu(boolean toggled) {
        if (this.leftSlideAnimation.isActive())
            return;

        if (toggled && !collapsableMenuToggled) {
            ((StackPane)this.root.getDrawableElement()).setAlignment(Pos.CENTER_LEFT);

            this.root.addChild(this.collapsableMenu);
            leftSlideAnimation.perform(true, this.root);

            this.collapsableMenuToggled = true;
        } else if (!toggled && collapsableMenuToggled) {
            ((StackPane)this.root.getDrawableElement()).setAlignment(Pos.CENTER_LEFT);

            leftSlideAnimation.perform(false, this.root);

            this.collapsableMenuToggled = false;
        }
    }

    private void runGameOfLifeSlideAnimation(boolean right) {
        if (this.golSlideAnimation.isActive()) return;
        this.golSlideAnimation.perform(right, this.root);
    }

    private ZStackGuiComponent initializeCollapsableMenu() {
        Color bgColor = BG_COLOR.deriveColor(0, 0, 0.9, 1);

        ZStackGuiComponent menu = new ZStackGuiComponent(450, 663);
        menu.offsetX(74);
        RectangleGuiComponent background = new RectangleGuiComponent(450, 663, bgColor);
        background.setStroke(5, ACCENT.deriveColor(0, 1, 1, 0.8));
        background.setRadius(10);
        VStackGuiComponent basicLayout = new VStackGuiComponent(30, Color.TRANSPARENT, 450, 550);
        basicLayout.setAlignment(Pos.TOP_CENTER);
        basicLayout.addChild(new LabelGuiComponent("Rule Book", 40, "Helvetica", Color.WHITE));

        menu.addChild(background);
        menu.addChild(basicLayout);

        return menu;
    }

    public void switchPerspective(final boolean rightPerspective) {
        if (golSlideAnimation.isActive() || leftSlideAnimation.isActive() || rightSlideAnimation.isActive()) return;
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(rightPerspective ? 0 : 400),event -> toggleCollapsableMenu(!rightPerspective));
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(rightPerspective ? 200 : 0),event -> toggleRightMenu(rightPerspective));
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(rightPerspective ? 170 : 100),event -> runGameOfLifeSlideAnimation(!rightPerspective));

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(keyFrame1);
        timeline.getKeyFrames().add(keyFrame2);
        timeline.getKeyFrames().add(keyFrame3);

        timeline.setCycleCount(1);
        timeline.play();
        animationsRunningProperty.set(true);
        timeline.setOnFinished(event -> {
            animationsRunningProperty.set(false);
        });
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
