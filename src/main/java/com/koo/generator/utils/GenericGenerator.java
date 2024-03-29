package com.koo.generator.utils;

import java.util.Random;

/**
 * @author gu.wq
 * @version 1.0
 * @type GenericGenerator
 * @desc
 * @date 2024/3/22 9:52
 */
public abstract  class GenericGenerator {
    public abstract String generate();

    private static Random random = null;

    protected Random getRandomInstance() {
        if (random == null) {
            random = new Random(System.currentTimeMillis());
        }

        return random;
    }
}
