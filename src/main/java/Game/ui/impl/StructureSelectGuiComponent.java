package Game.ui.impl;

import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import Game.ui.impl.button.ImagedButtonGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class StructureSelectGuiComponent extends GuiComponent {

    // Setup
    private final int ITEMS_PER_ROW = 2, ROWS_PER_PAGE = 3;
    private final int HORIZONTAL_SPACING = 7, MIN_SIDE_ROOM = 50;
    private final int VERTICAL_SPACING = 7, MIN_TB_ROOM = 100;

    // Automated
    private final int PAGE_HEIGHT, PAGE_WIDTH;
    private final int itemWidth;
    private final Color backgroundColor;

    private final GuiManager guiManager;

    private VBox drawableElement;

    private SimpleIntegerProperty selectedPageIndex;
    private ArrayList<VStackGuiComponent> pageElements;

    public StructureSelectGuiComponent(int pageWidth, int pageHeight, Color backgroundColor, GuiManager guiManager) {
        this.selectedPageIndex = new SimpleIntegerProperty(0);
        this.drawableElement = new VBox();
        this.guiManager = guiManager;
        this.PAGE_WIDTH = pageWidth;
        this.PAGE_HEIGHT = pageHeight;
        this.backgroundColor = backgroundColor;

        this.itemWidth = Math.min(pageWidth - MIN_SIDE_ROOM*2 - HORIZONTAL_SPACING*ITEMS_PER_ROW, pageHeight - MIN_TB_ROOM*2 - VERTICAL_SPACING*ROWS_PER_PAGE);
        this.pageElements = new ArrayList<>();
        makeRoom();

        this.addChild(this.pageElements.getFirst());
        this.addChild(initializePager());

        // TEmp
        for (int i = 0; i < 10; i++) {
            addElement();
        }
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
        HStackGuiComponent pager = new HStackGuiComponent(10, backgroundColor, 200, 20, 0);
        pager.addChild(ImagedButtonGuiComponent.fromUrl("https://www.iconexperience.com/_img/v_collection_png/256x256/shadow/arrow_left_green.png", 20, 20, true, backgroundColor, Color.GREEN, 2, 6, e -> {
            this.goToPage(selectedPageIndex.get() - 1);
        }));
        StringProperty text = new SimpleStringProperty("Page " + (selectedPageIndex.intValue()+1) + " / " + pageElements.size());
        selectedPageIndex.addListener((observable, oldValue, newValue) -> {
            text.set("Page " + (newValue.intValue() + 1) + " / " + pageElements.size());
        });
        pager.addChild(new LabelGuiComponent(text, 30, "Helvetica", Color.WHITE));
        pager.addChild(ImagedButtonGuiComponent.fromUrl("https://icons.iconarchive.com/icons/custom-icon-design/flat-cute-arrows/256/Arrow-Right-1-icon.png", 20, 20, true, backgroundColor, Color.GREEN, 2, 6, e -> {
            this.goToPage(selectedPageIndex.get() + 1);
        }));

        pager.setAlignment(Pos.BASELINE_CENTER);
        return pager;
    }

    public void addElement(/* Element element */) {
        RectangleGuiComponent tempElement = new RectangleGuiComponent(itemWidth, itemWidth, Color.BLACK.brighter().brighter().brighter());
        tempElement.setStroke(3, Color.GREEN.darker());
        tempElement.setRadius(15);
        makeRoom();
        this.pageElements.getLast().getLastChild().addChild(tempElement);
    }

    public void goToPage(int pageIndex) {
        this.selectedPageIndex.set(pageIndex);
        this.setChild(0, this.pageElements.get(pageIndex));
    }

    private void makeRoom() {
        if (pageElements.isEmpty()) {
            createNewPage();
        }

        VStackGuiComponent lastPage = pageElements.getLast();
        if (lastPage.getNumberOfChildren() == 0 || lastPage.getLastChild().getNumberOfChildren() == ITEMS_PER_ROW) {
            createNewRow(lastPage);
        }
    }

    private void createNewRow(VStackGuiComponent page) {
        if (page.getNumberOfChildren() == ROWS_PER_PAGE) {
            createNewPage();
        }

        page = pageElements.getLast();
        HStackGuiComponent newChild = new HStackGuiComponent(HORIZONTAL_SPACING, backgroundColor, 0);
        newChild.setAlignment(Pos.TOP_CENTER);
        page.addChild(newChild);
    }

    private void createNewPage() {
        VStackGuiComponent newComp = new VStackGuiComponent(HORIZONTAL_SPACING, backgroundColor);
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
