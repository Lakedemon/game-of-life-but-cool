package Game;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;

public class MyComboBox<T> extends ComboBox<T> {
    Region arrowBtn ;

    public MyComboBox() {
        super();
        customizeCellFactory();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if(arrowBtn==null){
            arrowBtn= (Region)lookup(".arrow-button");
            arrowBtn.setMaxSize(0,0);
            arrowBtn.setMinSize(0,0);
            arrowBtn.setPadding(new Insets(0));

            Region arrow= (Region)lookup(".arrow");
            arrow.setMaxSize(0,0);
            arrow.setMinSize(0,0);
            arrow.setPadding(new Insets(0));

            // Call again the super method to relayout with the new bounds.
            super.layoutChildren();
        }
    }

    private void customizeCellFactory(){
        setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }
}
