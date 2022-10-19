package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;

public class FastFigure extends Figure{
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
}
