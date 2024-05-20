package Game;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ColorPallet {
    private static class ValueColorPair {
        private final double value;
        private final Color color;

        public ValueColorPair(double value, Color color) {
            this.value = value;
            this.color = color;
        }

        public double getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    private final List<ValueColorPair> pallet = new ArrayList<>();

    public ColorPallet() {}

    public void addColor(double value, Color color) {
        pallet.add(new ValueColorPair(value, color));
    }

    public Color getColor(double value) {
        for (ValueColorPair pair : pallet) {
            if (pair.getValue() == value) {
                return pair.getColor();
            }
        }
        // Default color if value not found
        return Color.BLACK;
    }
}

