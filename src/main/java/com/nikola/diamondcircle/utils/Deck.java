package com.nikola.diamondcircle.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;

    public Deck() {
        loadCards();
    }

    public Card getCard() {
        var ret = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        deck.add(0, ret);
        return ret;
    }

    private void loadCards() {
        deck = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            deck.add(Card.ONE);
            deck.add(Card.TWO);
            deck.add(Card.THREE);
            deck.add(Card.FOUR);
            deck.add(Card.SPECIAL);
        }
        deck.add(Card.SPECIAL);
        deck.add(Card.SPECIAL);
        Collections.shuffle(deck);

    }
}
