package Game;
import Game.graphics.GraphicsHandler;
import Game.input.InputHandler;
import Game.rules.Rule;
import Game.rules.RuleBook;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private GraphicsHandler graphicsHandler;
    private InputHandler inputHandler;

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
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Init scene
        Scene scene = new Scene(root, width * CELL_SIZE, height * CELL_SIZE);

        // Set window properties
        primaryStage.setTitle("Game of life - but cool");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Init game of life
        RuleBook ruleBook = new RuleBook();

        int[] one = new int[]{1};
        int[] zero = new int[]{0};
        int[] any = new int[]{0, 1};

        Rule ruleA = new Rule(cell -> cell.getValue() == 1, count -> count < 1, 0, one);
        Rule ruleB = new Rule(cell -> cell.getValue() == 1, count -> count > 4, 0, one);
        Rule ruleC = new Rule(cell -> cell.getValue() == 1, count -> count == 3, 1, zero);
        ruleBook.addRule(ruleA);
        ruleBook.addRule(ruleB);
        ruleBook.addRule(ruleC);

        GameOfLife gameOfLife = new GameOfLife(width, height, ruleBook);
        Cell[][] grid = gameOfLife.getGrid();

        // Init handlers
        this.graphicsHandler = new GraphicsHandler(canvas);
        this.inputHandler = new InputHandler(gameOfLife, graphicsHandler);
        this.graphicsHandler.initCustomCursor(scene, root, this.inputHandler.getBrush());
        this.inputHandler.registerKeyHandlers(scene, canvas);

        // Init main game of life loop
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameOfLife.stepGen();
                graphicsHandler.fillGameCanvas(width, height, grid);
            }
        };

        // Start main game of life loop
        animationTimer.start();
    }
}
