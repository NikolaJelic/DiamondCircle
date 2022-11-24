package com.nikola.diamondcircle.player.figure;

import com.nikola.diamondcircle.utils.Color;

import java.util.Random;

public class FigureFactory {
    private final Random random = new Random();
    public Figure getRandomFigure(Integer maxPosition, Color color) {
        switch (random.nextInt(3)) {
            case 0 -> {
                return new RegularFigure(maxPosition, color);
            }
            case 1 -> {
                return new FastFigure(maxPosition, color);
            }
            case 2 -> {
                return new FlyingFigure(maxPosition, color);
            }
        }
        return new RegularFigure(maxPosition, color);
    }

}
