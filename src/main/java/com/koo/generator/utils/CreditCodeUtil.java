package com.koo.generator.utils;


import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gu.wq
 * @version 1.0
 * @type CreditCodeUtil
 * @desc
 * @date 2024/3/22 17:17
 */
public class CreditCodeUtil {

    private static final int[] WEIGHT = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    private static final char[] BASE_CODE_ARRAY = "0123456789ABCDEFGHJKLMNPQRTUWXY".toCharArray();
    private static final Map<Character, Integer> CODE_INDEX_MAP;

    static {
        CODE_INDEX_MAP = new ConcurrentHashMap<>();
        for (int i = 0; i < BASE_CODE_ARRAY.length; i++) {
            CODE_INDEX_MAP.put(BASE_CODE_ARRAY[i], i);
        }
    }

    public static void main(String[] args) {
        generatorPersonCard("张三集团", "张三", randomCreditCode());
    }

    public static String generatorPersonCard(String companyName, String legalPerson, String creditCode) {
        IdCardUtil tt = new IdCardUtil();
        tt.setFont("宋体", 18);
        //生成文件名
        String companyNamePath = companyName + "-营业执照.jpg";
        BufferedImage d = tt.loadImageUrl(ClassLoader.getSystemResource("yyzz.jpg"));

        //往图片上写文字
        //企业名称
        tt.writeImageLocal(companyNamePath, tt.modifyImage(d, companyName, 230, 300, 0));
        //法人
        tt.writeImageLocal(companyNamePath, tt.modifyImage(d, legalPerson, 230, 365, 0));
        // 社会信用代码
        tt.writeImageLocal(companyNamePath, tt.modifyImage(d, creditCode, 105, 205, 0));
        return companyNamePath;
    }

    /**
     * 获取一个随机的统一社会信用代码
     *
     * @return 统一社会信用代码
     */
    public static String randomCreditCode() {
        final StringBuilder buf = new StringBuilder(18);

        for (int i = 0; i < 2; i++) {
            int num = RandomUtils.randomInt(BASE_CODE_ARRAY.length - 1);
            buf.append(Character.toUpperCase(BASE_CODE_ARRAY[num]));
        }
        for (int i = 2; i < 8; i++) {
            int num = RandomUtils.randomInt(10);
            buf.append(BASE_CODE_ARRAY[num]);
        }
        for (int i = 8; i < 17; i++) {
            int num = RandomUtils.randomInt(BASE_CODE_ARRAY.length - 1);
            buf.append(BASE_CODE_ARRAY[num]);
        }

        final String code = buf.toString();
        return code + BASE_CODE_ARRAY[getParityBit(code)];
    }

    /**
     * 获取校验码
     *
     * @param creditCode 统一社会信息代码
     * @return 获取较验位的值
     */
    private static int getParityBit(CharSequence creditCode) {
        int sum = 0;
        Integer codeIndex;
        for (int i = 0; i < 17; i++) {
            codeIndex = CODE_INDEX_MAP.get(creditCode.charAt(i));
            if (null == codeIndex) {
                return -1;
            }
            sum += codeIndex * WEIGHT[i];
        }
        final int result = 31 - sum % 31;
        return result == 31 ? 0 : result;
    }
}
