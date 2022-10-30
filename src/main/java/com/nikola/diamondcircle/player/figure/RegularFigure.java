package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

public class RegularFigure extends Figure {

    public RegularFigure(Integer maxPosition, Color color) {
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {

        return "com/nikola/diamondcircle/assets/figures/regular.png";
    }


    @Override
    public void move(Integer steps) {
        int distance = diamondCount + steps;
        currentPosition += distance;
        if (currentPosition >= maxPosition) {
            setFinished(true);
        }
    }

    @Override
    public void interact(GameObject gameObject) {
        switch (gameObject) {
            case HOLE -> setAlive(false);
            case FIGURE -> incrementCurrentPosition();
            case COIN -> diamondCount++;
        }
    }

    @Override
    protected String getType() {
        return "regular";
    }
}
