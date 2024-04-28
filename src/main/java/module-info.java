module game.modifiedv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens game.modifiedv1 to javafx.fxml;
    exports game.modifiedv1;
    exports game.modifiedv1.graphics;
    opens game.modifiedv1.graphics to javafx.fxml;
}