package Game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        int height = 300;
        int width = 300;
        int cell_size = 2;

        Canvas canvas = new Canvas(width * cell_size, height * cell_size);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, width * cell_size, height * cell_size);
        primaryStage.setTitle("JavaFX test");
        primaryStage.setScene(scene);
        primaryStage.show();

        GameOfLife gameOfLife = new GameOfLife(width, height);
        int[][] grid = gameOfLife.getGrid();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameOfLife.stepGen();
                fillCanvas(gc, width, height, grid, cell_size);
            }
        };

        animationTimer.start();
    }

    void fillCanvas(GraphicsContext gc, int width, int height, int[][] grid, int cell_size){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] == 0) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j * cell_size, i * cell_size, cell_size, cell_size);
            }
        }
    }
}
