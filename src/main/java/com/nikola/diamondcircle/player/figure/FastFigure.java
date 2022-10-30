package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.game.GameObject;
import com.nikola.diamondcircle.utils.Color;

import static java.lang.Thread.sleep;

public class FastFigure extends Figure{

    public FastFigure(Integer maxPosition, Color color){
        super(maxPosition, color);
    }

    @Override
    public String getTexturePath() {
        return getPathPrefix()+ "fast.png";
    }

    @Override
    public void move(Integer steps) throws InterruptedException {

        int distance = diamondCount + steps * 2;
        for (int i = 0; i < distance; ++i) {
            if (currentPosition + 1 >= maxPosition) {
                setFinished(true);
            }
            ++currentPosition;
            sleep(1000);
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
