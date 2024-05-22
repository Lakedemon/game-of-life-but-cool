package Game;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ColorPallet {
    private final List<Color> pallet = new ArrayList<>();

    public ColorPallet(){}

    public List<Color> getPallet() {
        return pallet;
    }

    public void addColor(Color color){
        pallet.add(color);
    }
}
