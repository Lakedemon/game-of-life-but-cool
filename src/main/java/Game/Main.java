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
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private ColorPallet colorPallet;
    private GuiManager guiManager;

    public static final int CELL_SIZE = 2;

    @Override
    public void start(Stage primaryStage){
        int height = 300;
        int width = 300;
        CursorGraphicsHandler cursorGraphics = new CursorGraphicsHandler();

        this.guiManager = new GuiManager();
        this.guiManager.initializeGuiComponents();
        // Init canvas

        // Register canvas to root
        StackPane root = new StackPane(this.guiManager.getRoot().getDrawableElement());

        // Init scene
        Scene scene = new Scene(root);

        // Set window properties
        primaryStage.setTitle("Game of life - but cool");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Init game of life
        GameOfLife gameOfLife = getGameOfLife(width, height, guiManager.getRulePane());
        Cell[][] grid = gameOfLife.getGrid();

        // Start testing
        SaveHandler saveHandler = new SaveHandler();
        // For testing, save immediately after creation
        // saveHandler.saveGrid(gameOfLife, "TestSave");
        //
        // For testing, load immediately after creation
        int[][] loadedGrid = saveHandler.loadGrid("TestSave");
        gameOfLife.setGridValues(loadedGrid);
        // As of right now the grid does not load unless explicitly drawn, however loading from the database works
        // I suppose the grid should be explicitly drawn again once the load is complete somewhere
        // fillCanvas(gc, width, height, loadedGrid, cell_size);
        // End testing

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

    private static GameOfLife getGameOfLife(int width, int height, RulePane ruleBook) {
        ruleBook.newRuleHolder(new Rule(1, 1, 2, 0, IntComparators.LESS_THAN));
        ruleBook.newRuleHolder(new Rule(1, 1, 3, 0, IntComparators.GREATER_THAN));
        ruleBook.newRuleHolder(new Rule(0, 1, 3, 1, IntComparators.EQUAL_TO));

        return new GameOfLife(width, height, ruleBook.getAssociatedRuleBook());
    }
}

