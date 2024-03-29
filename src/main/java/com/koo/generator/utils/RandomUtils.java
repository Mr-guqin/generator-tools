package com.koo.generator.utils;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author gu.wq
 * @version 1.0
 * @type RandomUtils
 * @desc
 * @date 2024/3/27 11:17
 */
public class RandomUtils {
    private static final Random RANDOM = new Random();
    public static int nextInt(int startInclusive, int endExclusive) {
        return startInclusive == endExclusive ? startInclusive : startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }
    public static int randomInt(int limit) {
        return getRandom().nextInt(limit);
    }

    public static long nextLong(long startInclusive, long endExclusive) {
        return startInclusive == endExclusive ? startInclusive : (long)nextDouble((double)startInclusive, (double)endExclusive);
    }

    public static long nextLong() {
        return nextLong(0L, 9223372036854775807L);
    }


    public static double nextDouble(double startInclusive, double endInclusive) {
        return startInclusive == endInclusive ? startInclusive : startInclusive + (endInclusive - startInclusive) * RANDOM.nextDouble();
    }

    public static double nextDouble() {
        return nextDouble(0.0D, 1.7976931348623157E308D);
    }
}
