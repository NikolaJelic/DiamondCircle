package com.nikola.diamondcircle.utils;

public enum Card {
    ONE("com/nikola/diamondcircle/assets/cards/OneCard.png",1),
    TWO("com/nikola/diamondcircle/assets/cards/TwoCard.png",2),
    THREE("com/nikola/diamondcircle/assets/cards/ThreeCard.png",3),
    FOUR("com/nikola/diamondcircle/assets/cards/FourCard.png",4),
    SPECIAL("com/nikola/diamondcircle/assets/cards/SpecialCard.png",0),
    BACK("/com/nikola/diamondcircle/assets/cards/backCard.png",0);

    private final String card;
    private final int step;

    Card(String card, int step){
        this.card = card;
        this.step = step;
    }

    public String getCard(){return card;}
    public int getStep(){return step;}
}
