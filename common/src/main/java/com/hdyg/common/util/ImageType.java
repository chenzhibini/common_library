package com.hdyg.common.util;

public enum ImageType {
        CIRCLE("circle"),//圆形
        BLUR("blur"),//模糊
        FITXY("fitXy");//铺满imageview
        String type = null;

        ImageType(String type) {
            this.type = type;
        }
    }