package Game.structures;

import Game.paint.Painter;
import Game.ui.impl.panel.StructureSelectGuiComponent;

import java.util.ArrayList;
import java.util.List;

public class StructureManager {

    private StructureSelectGuiComponent component;
    private Painter painter;

    private final List<Structure> availableStructures;
    private int selectedStructure;

    public StructureManager() {
        availableStructures = new ArrayList<>();
        selectedStructure = -1;
        initStructures();
    }

    public StructureManager(ArrayList<Structure> structures) {
        if (!structures.isEmpty()) {
            availableStructures = structures;
        } else {
            availableStructures = new ArrayList<>();
            initStructures(); // Failsafe incase structures not in database
        }
        selectedStructure = -1;
    }

    public void setStructureSelectGuiComponent(StructureSelectGuiComponent component) {
        this.component = component;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    private void initStructures() {
        int[][] checkerboard = new int[32][32];

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                checkerboard[i][j] = (i + j) % 2;
            }
        }
        this.availableStructures.add(new Structure(checkerboard));
        int[][] grid2 = {{1, 0, 0, 1}, {0, 1, 1, 0}, {0, 1, 1, 0}, {1, 0, 0, 1}};
        this.availableStructures.add(new Structure(grid2));
    }

    public List<Structure> getAvailableStructures() {
        return availableStructures;
    }

    // If the same is selected twice, deselect a structure and go back to basics
    public void attemptChangeSelectedStructure(int indexOfStructure) {
        if (indexOfStructure == selectedStructure) {
            // Deselect
            this.selectedStructure = -1;
        } else {
            this.selectedStructure = indexOfStructure;
        }

        notifyPartiesOfSelectionUpdate(this.selectedStructure);
    }

    private void notifyPartiesOfSelectionUpdate(int indexOfStructure) {
        component.updateSelectionGraphics(indexOfStructure);
        painter.handleStructureSelect(indexOfStructure >= 0 ? this.availableStructures.get(indexOfStructure) : null);
    }
}
