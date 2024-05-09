package Game.ui.clicking;

import Game.ui.GuiComponent;

public abstract class ClickableGuiComponent extends GuiComponent {

    private ClickEvent clickEventHandler;

    public ClickableGuiComponent() {
        setOnClick(e -> {});
    }

    public void setOnClick(ClickEvent eventHandler) {
        this.clickEventHandler = eventHandler;
    }

    protected void registerClicks() {
        this.getDrawableElement().setOnMouseClicked(this.clickEventHandler::click);
    }

}
