package Game.structures;

import Game.ui.impl.StructureSelectGuiComponent;

import java.util.ArrayList;
import java.util.List;

public class StructureManager {

    private StructureSelectGuiComponent component;
    private final List<Structure> availableStructures;
    private int selectedStructure;

    public StructureManager() {
        availableStructures = new ArrayList<>();
        selectedStructure = -1;
        initStructures();
    }

    public void setStructureSelectGuiComponent(StructureSelectGuiComponent component) {
        this.component = component;
    }

    private void initStructures() {
        int[][] grid1 = {{1, 0, 1, 0, 1, 0, 1, 0},{0, 1, 0, 1, 0, 1, 0, 1},{1, 0, 1, 0, 1, 0, 1, 0},{0, 1, 0, 1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0},{0, 1, 0, 1, 0, 1, 0, 1},{1, 0, 1, 0, 1, 0, 1, 0},{0, 1, 0, 1, 0, 1, 0, 1}};
        this.availableStructures.add(new Structure(grid1));
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
    }
}
