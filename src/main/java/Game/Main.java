package Game;
import Game.input.InputHandler;
import Game.paint.ColorPallet;
import Game.paint.Painter;
import Game.rules.Rule;
import Game.ui.GuiManager;
import Game.ui.cursor.CursorGraphicsHandler;
import Game.save_system.SaveHandler;
import Game.rules.*;
import Game.rules.Comparators.IntComparators;
import Game.ui.impl.rule.RulesGuiComponent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private ColorPallet colorPallet;
    private GuiManager guiManager;
    private SaveHandler saveHandler;

    public static final int CELL_SIZE = 2;

    @Override
    public void start(Stage primaryStage){
        int height = 300;
        int width = 300;

        // Init game of life
        GameOfLife gameOfLife = getGameOfLife(width, height);
        this.saveHandler = new SaveHandler(gameOfLife);
        this.guiManager = new GuiManager(saveHandler);
        this.guiManager.initializeGuiComponents();

        gameOfLife.setRulePane(this.guiManager.getRulePane());

        Cell[][] grid = gameOfLife.getGrid();

        // Start testing
        // For testing, save immediately after creation
        saveHandler.saveGrid("TestSave");

        // For testing, load immediately after creation
        saveHandler.loadGrid("TestSave");
        // As of right now the grid does not load unless explicitly drawn, however loading from the database works
        // I suppose the grid should be explicitly drawn again once the load is complete somewhere
        // fillCanvas(gc, width, height, loadedGrid, cell_size);
        // End testing

        CursorGraphicsHandler cursorGraphics = new CursorGraphicsHandler();

        // Init canvas

        // Register canvas to root
        StackPane root = new StackPane(this.guiManager.getRoot().getDrawableElement());

        // Init scene
        Scene scene = new Scene(root, GuiManager.STAGE_WIDTH, GuiManager.STAGE_HEIGHT);
        this.guiManager.initializeAnimations();

        System.out.println("Scene width: " + scene.getWidth());

        // Set window properties
        primaryStage.setTitle("Game of life - but cool");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


        // Init handlers
        this.guiManager.getGameOfLifeGuiComponent().getColorPallet().addColor(0, Color.BLACK);
        this.guiManager.getGameOfLifeGuiComponent().getColorPallet().addColor(1, Color.DARKRED);

        Painter painter = new Painter(gameOfLife, cursorGraphics);
        InputHandler inputHandler = new InputHandler(painter);

        cursorGraphics.initCustomCursor(this.guiManager.getGameOfLifeCanvas(), (StackPane) this.guiManager.getGameOfLifeGuiComponent().getDrawableElement(), painter.getBrush());
        inputHandler.registerKeyHandlers(scene, this.guiManager);

        // Init main game of life loop
        AnimationTimer animationTimer = new AnimationTimer() {
            int count = 0;
            long currentNs = System.nanoTime();
            @Override
            public void handle(long l) {
                gameOfLife.stepGen();

                guiManager.getGameOfLifeGuiComponent().getColorPallet().updatePallet(gameOfLife.getRuleBook().getValueSet());
                guiManager.getGameOfLifeGuiComponent().refreshGameOfLifeCanvas(grid);

                if (System.nanoTime() - currentNs > 1000000000) {
                    System.out.println("TPS/FPS: " + count);
                    currentNs = System.nanoTime();
                    count = 0;
                }

                count++;
            }
        };

        // Start main game of life loop
        animationTimer.start();
    }

    private static GameOfLife getGameOfLife(int width, int height) {
        return new GameOfLife(width, height);
    }
}

