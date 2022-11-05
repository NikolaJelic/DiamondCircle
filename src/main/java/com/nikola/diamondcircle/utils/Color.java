package com.nikola.diamondcircle.utils;

public enum Color {
        RED("#e43b44"),
        GREEN("#63c74d"),
        BLUE("#0099db"),
        YELLOW("#feae34"),
        BROWN("#733e39"),
        DARK_BROWN("#3e2731"),
        DARK_BLUE("#262b44"),
        BLACK("#181425");

        private final String colorValue;

        Color(String colorValue){
            this.colorValue = colorValue;
        }

        public  String getColorValue() {
            return colorValue;
        }
}
