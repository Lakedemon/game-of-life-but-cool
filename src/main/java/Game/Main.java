package Game;
import Game.input.InputHandler;
import Game.paint.Painter;
import Game.rules.Rule;
import Game.rules.RuleBook;
import Game.ui.GuiHandler;
import Game.ui.cursor.CursorGraphicsHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private CursorGraphicsHandler cursorGraphics;
    private InputHandler inputHandler;
    private GuiHandler guiHandler;

    public static final int CELL_SIZE = 2;

    @Override
    public void start(Stage primaryStage){

        int height = 300;
        int width = 300;

        this.guiHandler = new GuiHandler();
        this.guiHandler.initializeGuiComponents();
        // Init canvas

        this.cursorGraphics = new CursorGraphicsHandler();

        // Register canvas to root
        StackPane root = new StackPane(this.guiHandler.getRoot().getDrawableElement());

        // Init scene
        Scene scene = new Scene(root);

        // Set window properties
        primaryStage.setTitle("Game of life - but cool");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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

        Painter painter = new Painter(gameOfLife, cursorGraphics);
        this.inputHandler = new InputHandler(painter);

        this.cursorGraphics.initCustomCursor(scene, root, painter.getBrush());
        this.inputHandler.registerKeyHandlers(this.guiHandler);

        // Init main game of life loop
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameOfLife.stepGen();
                guiHandler.getGameOfLifeGuiComponent().refreshGameOfLifeCanvas(grid);
            }
        };

        // Start main game of life loop
        animationTimer.start();
    }
}
