package Game.ui.impl;

import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import Game.ui.clicking.ClickEvent;
import Game.ui.impl.button.ImagedButtonGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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

    private final GuiManager guiManager;

    private VBox drawableElement;

    private SimpleIntegerProperty selectedPageIndex;
    private ArrayList<VStackGuiComponent> pageElements;

    public StructureSelectGuiComponent(int pageWidth, int pageHeight, Color backgroundColor, GuiManager guiManager) {
        this.selectedPageIndex = new SimpleIntegerProperty(0);
        this.drawableElement = new VBox();
        this.drawableElement.setAlignment(Pos.BOTTOM_CENTER);
        this.drawableElement.setMaxHeight(pageHeight);
        this.drawableElement.setMaxWidth(pageWidth);
        this.drawableElement.setMinHeight(pageHeight);
        this.drawableElement.setMinWidth(pageWidth);

        this.guiManager = guiManager;
        this.PAGE_WIDTH = pageWidth;
        this.PAGE_HEIGHT = pageHeight;
        this.backgroundColor = backgroundColor;

        this.itemWidth = Math.min(pageWidth - MIN_SIDE_ROOM*2 - HORIZONTAL_SPACING * (ITEMS_PER_ROW-1), pageHeight - MIN_TB_ROOM*2 - VERTICAL_SPACING*(ROWS_PER_PAGE - 1));
        this.pageElements = new ArrayList<>();

        // TEmp
        for (int i = 0; i < 21; i++) {
            addElement();
        }

        this.addChild(this.pageElements.getFirst());
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
