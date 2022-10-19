package com.nikola.diamondcircle.player.figure;

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
}
