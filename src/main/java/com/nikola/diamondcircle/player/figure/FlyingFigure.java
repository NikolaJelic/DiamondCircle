package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

public class FlyingFigure extends Figure {
    public FlyingFigure(Integer maxPosition, Color color) {
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {
        return getPathPrefix() + "flying.png";
    }

    @Override
    public Integer getDistance(Integer steps) {
        return diamondCount + steps;
    }

    @Override
    public void interact(GameObject gameObject) {
        if (gameObject == GameObject.COIN) {
            diamondCount++;
        }
    }

    @Override
    protected String getType() {
        return "flying";
    }
}
