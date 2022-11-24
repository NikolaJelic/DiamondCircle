package com.nikola.diamondcircle.utils;

public enum Color {
        RED("#e43b44"),
        GREEN("#63c74d"),
        BLUE("#0099db"),
        YELLOW("#feae34");

        private final String colorValue;

        Color(String colorValue){
            this.colorValue = colorValue;
        }

        public  String getColorValue() {
            return colorValue;
        }
}
