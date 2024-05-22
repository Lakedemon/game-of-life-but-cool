package Game;

import java.util.*;

import javafx.scene.paint.Color;

public class ColorPallet {
    private final Map<Integer, Color> pallet = new HashMap<>();

    public ColorPallet(Set<Integer> vals) {
        updatePallet(vals);
    }

    public ColorPallet(){}

    private Color randomColor(){
        return new Color(Math.random(), Math.random(), Math.random(), 1.0);
    }

    public void updatePallet(Set<Integer> vals) {
        for (Integer val : vals) {
            if (!pallet.containsKey(val)) {
                pallet.put(val, randomColor());
            }
        }
    }

    public void addColor(Integer value, Color color) {
        pallet.put(value, color);
    }

    public Color getColor(Integer value) {
        return pallet.get(value);
    }
}

