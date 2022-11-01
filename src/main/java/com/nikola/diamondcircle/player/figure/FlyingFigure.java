package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import static java.lang.Thread.sleep;

public class FlyingFigure extends Figure{
    @Override
    public String getTexturePath() {
        return getPathPrefix()+ "flying.png";
    }

    public FlyingFigure(Integer maxPosition, Color color){
        super(maxPosition, color);
    }
    @Override
    public Integer getDistance(Integer steps) {
        return diamondCount + steps;
    }

    @Override
    public void interact(GameObject gameObject) {
        switch (gameObject){
            case FIGURE -> move();
            case COIN -> diamondCount++;
        }
    }

    @Override
    protected String getType() {
        return "flying";
    }
}
