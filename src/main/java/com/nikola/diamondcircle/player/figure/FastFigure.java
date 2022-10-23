package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

public class FastFigure extends Figure{

    public FastFigure(Integer maxPosition, Color color){
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {
        return "com/nikola/diamondcircle/assets/figures/flying.png";
    }

    @Override
    public void move(Integer steps) {
        int distance = diamondCount + steps * 2;
        currentPosition += distance;
        if(currentPosition >= maxPosition){
            setFinished(true);
        }
    }

    @Override
    public void interact(GameObject gameObject) {
        switch (gameObject){
            case HOLE -> setAlive(false);
            case FIGURE -> incrementCurrentPosition();
            case COIN -> diamondCount++;
        }
    }

    @Override
    protected String getType() {
        return "fast";
    }
}
