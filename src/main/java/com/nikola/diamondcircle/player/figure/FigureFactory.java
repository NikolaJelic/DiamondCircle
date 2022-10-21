package com.nikola.diamondcircle.player.figure;

import java.util.Random;

public class FigureFactory {
    public Figure getRandomFigure(Integer maxPosition){
        switch (new Random().nextInt(4)){
            case 0 -> {
                return new RegularFigure(maxPosition);
            }
            case 1 -> {
                return new FastFigure(maxPosition);
            }
            case 2 -> {
                return new FlyingFigure(maxPosition);
            }
        }
    return new RegularFigure(maxPosition);
    }

}
