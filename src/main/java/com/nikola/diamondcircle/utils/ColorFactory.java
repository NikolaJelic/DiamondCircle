package com.nikola.diamondcircle.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorFactory {

    private static List<Color> possibleColors = new ArrayList<>(List.of(new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW}));

    public static Color getColor() {
        Random random = new Random();
        int randomNumber = random.nextInt(possibleColors.size());
        Color ret = possibleColors.get(randomNumber);
        possibleColors.remove(randomNumber);
        return ret;
    }
}
