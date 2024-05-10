package Game;
import Game.input.InputHandler;
import Game.paint.Painter;
import Game.rules.Rule;
import Game.rules.RuleBook;
import Game.ui.GuiManager;
import Game.ui.cursor.CursorGraphicsHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private GuiManager guiManager;

    public static final int CELL_SIZE = 2;

    @Override
    public void start(Stage primaryStage){

        int height = 300;
        int width = 300;
        CursorGraphicsHandler cursorGraphics = new CursorGraphicsHandler();

        this.guiManager = new GuiManager(cursorGraphics);
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
        RuleBook ruleBook = new RuleBook();

        int[] one = new int[]{1};
        int[] zero = new int[]{0};
        int[] any = new int[]{0, 1};

        Rule ruleA = new Rule(cell -> cell.getValue() == 1, count -> count < 2, 0, one);
        Rule ruleB = new Rule(cell -> cell.getValue() == 1, count -> count > 3, 0, one);
        Rule ruleC = new Rule(cell -> cell.getValue() == 1, count -> count == 3, 1, zero);
        ruleBook.addRule(ruleA);
        ruleBook.addRule(ruleB);
        ruleBook.addRule(ruleC);

        GameOfLife gameOfLife = new GameOfLife(width, height, ruleBook);
        Cell[][] grid = gameOfLife.getGrid();

        // Init handlers

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
}
