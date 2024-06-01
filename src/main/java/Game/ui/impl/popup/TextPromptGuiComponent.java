package Game.ui.impl.popup;

import Game.ui.GuiComponent;
import Game.ui.animations.Animation;
import Game.ui.animations.impl.SlideAnimation;
import Game.ui.impl.LabelGuiComponent;
import Game.ui.impl.TextInputGuiComponent;
import Game.ui.impl.button.LabeledButtonGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import Game.ui.impl.stack.ZStackGuiComponent;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextPromptGuiComponent extends ZStackGuiComponent {

    private TextPromptSubmitAction action;
    private final SlideAnimation animation;

    private boolean open = false;

    public TextPromptGuiComponent(String prompt, String textPlaceholder) {
        super(300, 150);
        this.setAlignment(Pos.CENTER);

        this.animation = new SlideAnimation(0, 0, -getHeight()*3, 0, this, 300, Animation.Easing.CUBIC_EASE_OUT);

        RectangleGuiComponent background = new RectangleGuiComponent(300, 150, new Color(0.2, 0.2, 0.2, 0.8));
        background.setRadius(5);
        background.setStroke(3, new Color(0.3, 0.3, 0.6, 1));

        this.addChild(background);

        VStackGuiComponent fields = new VStackGuiComponent(4, Color.TRANSPARENT);
        fields.setAlignment(Pos.CENTER);
        fields.addChild(new LabelGuiComponent(prompt, 30, "Helvetica", Color.WHITE));

        TextInputGuiComponent textInput = new TextInputGuiComponent(textPlaceholder, Color.DARKGRAY.darker().darker(), 100, 30);
        fields.addChild(textInput);

        HStackGuiComponent buttonsSection = new HStackGuiComponent(13, Color.TRANSPARENT, 13);
        buttonsSection.addChild(new LabeledButtonGuiComponent("Cancel", Font.getDefault(), Color.WHITE.darker(), 60, 30, Color.DARKGRAY.darker().darker(), Color.INDIANRED, 5, 1, e -> {
            this.action.actionPerformed(textInput.getText(), false);
        }));
        buttonsSection.addChild(new LabeledButtonGuiComponent("Proceed", Font.getDefault(), Color.WHITE.darker(), 60, 30, Color.DARKGRAY.darker().darker(), new Color(0.3, 0.6, 0.3, 1).brighter(), 5, 1, e -> {
            this.action.actionPerformed(textInput.getText(), true);
        }));

        fields.addChild(new RectangleGuiComponent(3, 5, Color.TRANSPARENT));
        fields.addChild(buttonsSection);
        buttonsSection.setAlignment(Pos.CENTER);

        this.addChild(fields);
    }

    public void attemptOpen(GuiComponent root, TextPromptSubmitAction additionalAction) {
        if (open) return;

        this.setAction((input, proceeded) -> {
            additionalAction.actionPerformed(input, proceeded);
            this.performAnimation(false, root);
            this.open = false;
        });

        root.addChild(this);
        this.performAnimation(true, root);
        this.open = true;
    }

    public boolean isOpen() {
        return open;
    }

    public void performAnimation(boolean in, GuiComponent root) {
        if (!this.animation.isActive()) {
            this.animation.perform(in, root);
        }
    }

    public void setAction(TextPromptSubmitAction action) {
        this.action = action;
    }

}
