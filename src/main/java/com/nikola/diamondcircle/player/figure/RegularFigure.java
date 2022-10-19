package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;

public class RegularFigure extends Figure{
    @Override
    public String getTexturePath() {
        return "com/nikola/diamondcircle/assets/figures/regular.png";
    }

    @Override
    public void move(Integer steps) {
        int distance = diamondCount + steps;
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
