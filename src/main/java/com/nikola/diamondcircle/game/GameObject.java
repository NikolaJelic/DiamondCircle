package com.nikola.diamondcircle.game;

import java.io.File;

public enum GameObject {
    FIGURE(""), COIN("coin.png"), EMPTY("Ground.png"), HOLE("Hole.png"), VISITED("visited.png");

    private final String prefix = "com" + File.separator + "nikola" + File.separator + "diamondcircle" + File.separator + "assets" + File.separator + "drops" + File.separator;
    private final String texture;

    GameObject(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return prefix + texture;
    }
}
