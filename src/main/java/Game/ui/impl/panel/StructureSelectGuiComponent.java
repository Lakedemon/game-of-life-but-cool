package Game.ui.impl.panel;

import Game.structures.Structure;
import Game.structures.StructureManager;
import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import Game.ui.clicking.ClickEvent;
import Game.ui.impl.CanvasGuiComponent;
import Game.ui.impl.LabelGuiComponent;
import Game.ui.impl.button.ImagedButtonGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import static Game.file.StaticFileHandler.*;

public class StructureSelectGuiComponent extends GuiComponent {

    // Setup
    private final int ITEMS_PER_ROW = 2, ROWS_PER_PAGE = 3;
    private final int HORIZONTAL_SPACING = 7, MIN_SIDE_ROOM = 20;
    private final int VERTICAL_SPACING = 7, MIN_TB_ROOM = 175;

    // Automated
    private final int PAGE_HEIGHT, PAGE_WIDTH;
    private final int itemWidth;
    private final Color backgroundColor;
    private final Color unselectedColor = Color.LIGHTSLATEGRAY.brighter();
    private final Color selectedColor = Color.YELLOW;

    private final GuiManager guiManager;
    private final StructureManager structureManager;

    private ZStackGuiComponent currentlySelected;

    private VBox drawableElement;

    private SimpleIntegerProperty selectedPageIndex;
    private ArrayList<VStackGuiComponent> pageElements;

    public StructureSelectGuiComponent(int pageWidth, int pageHeight, Color backgroundColor, GuiManager guiManager, StructureManager structureManager) {
        this.selectedPageIndex = new SimpleIntegerProperty(0);
        this.drawableElement = new VBox();
        this.drawableElement.setAlignment(Pos.BOTTOM_CENTER);
        this.drawableElement.setMaxHeight(pageHeight);
        this.drawableElement.setMaxWidth(pageWidth);
        this.drawableElement.setMinHeight(pageHeight);
        this.drawableElement.setMinWidth(pageWidth);

        this.structureManager = structureManager;
        this.guiManager = guiManager;
        this.PAGE_WIDTH = pageWidth;
        this.PAGE_HEIGHT = pageHeight;
        this.backgroundColor = backgroundColor;

        this.itemWidth = Math.min(pageWidth - MIN_SIDE_ROOM*2 - HORIZONTAL_SPACING * (ITEMS_PER_ROW-1), pageHeight - MIN_TB_ROOM*2 - VERTICAL_SPACING*(ROWS_PER_PAGE - 1));
        this.pageElements = new ArrayList<>();

        for (int i = 0; i < this.structureManager.getAvailableStructures().size(); i++) {
            this.addElement(i, this.structureManager.getAvailableStructures().get(i));
        }

        this.addChild(this.pageElements.get(0));
        this.addChild(initializePager());
    }

    @Override
    public void addChild(GuiComponent child) {
        super.addChild(child);
        this.drawableElement.getChildren().add(child.getDrawableElement());
    }

    @Override
    public void setChild(int index, GuiComponent child) {
        super.setChild(index, child);
        this.drawableElement.getChildren().set(index, child.getDrawableElement());
    }

    private HStackGuiComponent initializePager() {
        HStackGuiComponent pager = new HStackGuiComponent(20, backgroundColor, 200, 20, 10);

        // Left button
        Optional<ImagedButtonGuiComponent> leftButton = initializeLeftPageButton();
        leftButton.ifPresent(pager::addChild);

        // Text
        StringProperty text = new SimpleStringProperty("Page " + (selectedPageIndex.intValue()+1) + " / " + pageElements.size());
        selectedPageIndex.addListener((observable, oldValue, newValue) -> text.set("Page " + (newValue.intValue() + 1) + " / " + pageElements.size()));
        pager.addChild(new LabelGuiComponent(text, 30, "Helvetica", Color.WHITE));

        // Right button
        Optional<ImagedButtonGuiComponent> rightButton = initializeRightPageButton();
        rightButton.ifPresent(pager::addChild);

        pager.setAlignment(Pos.CENTER);

        return pager;
    }

    private Optional<ImagedButtonGuiComponent> initializeLeftPageButton() {
        ClickEvent leftClick  = e -> {
            if (selectedPageIndex.get() > 0 && selectedPageIndex.get() < pageElements.size())
                this.goToPage(selectedPageIndex.get() - 1);
        };

        return getImagedButtonGuiComponent(leftClick, LEFT_ARROW_IMG_RESOURCE_PATH);
    }

    private Optional<ImagedButtonGuiComponent> initializeRightPageButton() {
        ClickEvent rightClick  = e -> {
            if (selectedPageIndex.get() >= 0 && selectedPageIndex.get() < pageElements.size() - 1)
                this.goToPage(selectedPageIndex.get() + 1);
        };

        return getImagedButtonGuiComponent(rightClick, RIGHT_ARROW_IMG_RESOURCE_PATH);
    }

    private Optional<ImagedButtonGuiComponent> getImagedButtonGuiComponent(ClickEvent leftClick, String rightArrowImgResourcePath) {
        Optional<InputStream> rightArrowStream = getImageInputStream(rightArrowImgResourcePath);
        if (rightArrowStream.isEmpty()) {
            System.out.println("ERROR: Failed to load settings menu button.");
            return Optional.empty();
        }

        ImagedButtonGuiComponent component = ImagedButtonGuiComponent.fromInputStream(
                rightArrowStream.get(), 45, 45, true, GuiManager.BG_COLOR, GuiManager.ACCENT, 1, 10, leftClick
        );

        component.maskBlackPixels(GuiManager.ACCENT);
        return Optional.of(component);
    }

    public void addElement(int index, Structure structure) {
        ZStackGuiComponent element = new ZStackGuiComponent(itemWidth, itemWidth);
        RectangleGuiComponent background = new RectangleGuiComponent(itemWidth, itemWidth, Color.BLACK.brighter().brighter().brighter());
        background.setStroke(3, this.unselectedColor);
        background.setRadius(15);
        element.addChild(background);

        CanvasGuiComponent actual = getCanvasGuiComponent(structure);
        element.addChild(actual);
        element.setAlignment(Pos.CENTER);

        makeRoom();
        element.getDrawableElement().setOnMouseClicked(e -> this.structureManager.attemptChangeSelectedStructure(index));
        this.pageElements.get(pageElements.size()-1).getLastChild().addChild(element);
    }

    public void updateSelectionGraphics(int newSelectedIndex) {
        if (newSelectedIndex == -1 || this.currentlySelected != null) {
            ((RectangleGuiComponent)this.currentlySelected.getChild(0)).setStroke(3, this.unselectedColor);
            this.currentlySelected = null;
        }
        if (newSelectedIndex == -1) return;

        int pageNum = newSelectedIndex / (this.ROWS_PER_PAGE * this.ITEMS_PER_ROW);
        int rowNum = (newSelectedIndex % (this.ROWS_PER_PAGE * this.ITEMS_PER_ROW)) / this.ITEMS_PER_ROW;
        int elementNum = newSelectedIndex % this.ITEMS_PER_ROW;
        ZStackGuiComponent element = (ZStackGuiComponent) ((HStackGuiComponent)
                this.pageElements.get(pageNum) // Page
                .getChild(rowNum)) // Row
                .getChild(elementNum); // Element

        if (element != null && element != this.currentlySelected) {
            ((RectangleGuiComponent)element.getChild(0)).setStroke(3, this.selectedColor);
            this.currentlySelected = element;
        }
    }

    private CanvasGuiComponent getCanvasGuiComponent(Structure structure) {
        CanvasGuiComponent actual = new CanvasGuiComponent(itemWidth, itemWidth);
        double dotSize = ((double)itemWidth) / (double)structure.getGrid().length;
        actual.setFill(Color.DARKGRAY);
        for (int i = 0; i < structure.getGrid().length; i++) {
            for (int j = 0; j < structure.getGrid()[i].length; j++) {
                if (structure.getGrid()[i][j].getValue() == 1) {
                    actual.drawRect(3+i*dotSize, 3+j*dotSize, dotSize, dotSize);
                }
            }
        }
        return actual;
    }

    public void goToPage(int pageIndex) {
        this.selectedPageIndex.set(pageIndex);
        this.setChild(0, this.pageElements.get(pageIndex));
    }

    private void makeRoom() {
        if (pageElements.isEmpty()) {
            createNewPage();
        }

        VStackGuiComponent lastPage = pageElements.get(pageElements.size()-1);
        if (lastPage.getNumberOfChildren() == 0 || lastPage.getLastChild().getNumberOfChildren() == ITEMS_PER_ROW) {
            createNewRow(lastPage);
        }
    }

    private void createNewRow(VStackGuiComponent page) {
        if (page.getNumberOfChildren() == ROWS_PER_PAGE) {
            createNewPage();
        }

        page = pageElements.get(pageElements.size()-1);
        HStackGuiComponent newChild = new HStackGuiComponent(HORIZONTAL_SPACING, backgroundColor, 0);
        newChild.setAlignment(Pos.TOP_CENTER);
        page.addChild(newChild);
    }

    private void createNewPage() {
        VStackGuiComponent newComp = new VStackGuiComponent(HORIZONTAL_SPACING, backgroundColor, this.PAGE_WIDTH, this.PAGE_HEIGHT);
        newComp.setAlignment(Pos.CENTER_LEFT);
        pageElements.add(newComp);
    }

    @Override
    public int getWidth() {
        return this.PAGE_WIDTH;
    }

    @Override
    public int getHeight() {
        return this.PAGE_HEIGHT;
    }

    @Override
    public Node getDrawableElement() {
        return drawableElement;
    }
}
