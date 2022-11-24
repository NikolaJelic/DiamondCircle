package com.nikola.diamondcircle.utils;

import java.io.File;

public enum Card {
    ONE("OneCard.png", 1),
    TWO("TwoCard.png", 2),
    THREE("ThreeCard.png", 3),
    FOUR("FourCard.png", 4),
    SPECIAL("SpecialCard.png", 0),
    BACK("backCard.png", 0);

    private final String cardTexturePath;
    private final int step;

    private final String pathPrefix = "com" + File.separator + "nikola" + File.separator + "diamondcircle" + File.separator + "assets" + File.separator + "cards" + File.separator;

    Card(String cardTexturePath, int step) {
        this.cardTexturePath = cardTexturePath;
        this.step = step;
    }

    public String getCardTexturePath() {
        return pathPrefix + cardTexturePath;
    }

    public int getStep() {
        return step;
    }
}
