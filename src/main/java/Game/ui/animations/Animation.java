package Game.ui.animations;

import Game.ui.GuiComponent;

public abstract class Animation {

    protected final Easing easing;

    public Animation(Easing easing) {
        this.easing = easing;
    }

    public abstract void perform(boolean in, GuiComponent root);

    public enum Easing {
        LINEAR, CUBIC_EASE_OUT;

        public double calculateEasedT(int tick, int maxTicks) {
            switch (this) {
                case LINEAR:
                    return (double) tick / (double) maxTicks;
                case CUBIC_EASE_OUT:
                    double t = (double) tick / (double) maxTicks;
                    t = Math.min(t, 1); // Ensure t does not exceed 1
                    return 1 - Math.pow(1 - t, 3);
            };

            return (double) tick / (double) maxTicks;
        }
    }

}
