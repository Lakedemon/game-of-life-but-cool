package Game;
import Game.save_system.SaveHandler;
import Game.graphics.GraphicsHandler;
import Game.rules.*;
import Game.rules.Comparators.IntComparators;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class Main extends Application {

    private GraphicsHandler graphicsHandler;
    private InputHandler inputHandler;
    private ColorPallet colorPallet;

    public static final int CELL_SIZE = 2;

    @Override
    public void start(Stage primaryStage){
        int height = 300;
        int width = 300;

        // Init canvas
        Canvas canvas = new Canvas(width * CELL_SIZE, height * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);

        // Register canvas to root
        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        // Init scene
        Scene scene = new Scene(root, width * CELL_SIZE + 400, height * CELL_SIZE);

        // Set window properties
        primaryStage.setTitle("Game of life - but cool");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Init game of life
        GameOfLife gameOfLife = getGameOfLife(width, height);
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

        //Rules
        RulePane ruleBox = new RulePane(gameOfLife.getRuleBook(), 5);
        root.setLeft(ruleBox);

        // Init handlers

        this.colorPallet = new ColorPallet();
        //colorPallet.addColor(0, Color.BLACK);
        //colorPallet.addColor(1, Color.WHITE);

        this.graphicsHandler = new GraphicsHandler(canvas, colorPallet);
        this.inputHandler = new InputHandler(gameOfLife, graphicsHandler);
        this.graphicsHandler.initCustomCursor(scene, root, this.inputHandler.getBrush());
        this.inputHandler.registerKeyHandlers(scene, canvas);

        // Init main game of life loop
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameOfLife.stepGen();
                colorPallet.updatePallet(gameOfLife.getRuleBook().getValueSet());
                graphicsHandler.fillGameCanvas(width, height, grid);
            }
        };

        // Start main game of life loop
        animationTimer.start();
    }

    private static GameOfLife getGameOfLife(int width, int height) {
        RuleBook ruleBook = new RuleBook();

        ruleBook.addRule(new Rule(1, 1, 2, 0, IntComparators.LESS_THAN));
        ruleBook.addRule(new Rule(1, 1, 3, 0, IntComparators.GREATER_THAN));
        ruleBook.addRule(new Rule(0, 1, 3, 1, IntComparators.EQUAL_TO));

        return new GameOfLife(width, height, ruleBook);
    }
}

