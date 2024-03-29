package com.koo.generator.generator;

import com.koo.generator.model.ChineseAreaList;
import com.koo.generator.utils.DateUtil;
import com.koo.generator.utils.GenericGenerator;
import com.koo.generator.utils.RandomUtils;
import com.koo.generator.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gu.wq
 * @version 1.0
 * @type ChineseIDCardNumberGenerator
 * @desc
 * @date 2024/3/22 10:55
 */
public class ChineseIDCardNumberGenerator extends GenericGenerator {

    private static ChineseIDCardNumberGenerator instance = new ChineseIDCardNumberGenerator();

    private ChineseIDCardNumberGenerator() {
    }

    public static ChineseIDCardNumberGenerator getInstance() {
        return instance;
    }

    /**
     * 生成签发机关：XXX公安局/XX区分局
     * Authority
     */
    public static String generateIssueOrg() {
        return ChineseAreaList.cityNameList
                .get(RandomUtils.nextInt(0, ChineseAreaList.cityNameList.size()))
                + "公安局";
    }

    /**
     * 生成有效期限：20150906-20350906
     */
    public static String generateValidPeriod() {
        Date beginDate = randomDate();
        Date endDate = DateUtil.addYear(beginDate, 20);
        return DateUtil.format(beginDate, DateUtil.DATE_FORMAT_YYYYMMDD) + "-" + DateUtil.format(endDate, DateUtil.DATE_FORMAT_YYYYMMDD);
    }

    @Override
    public String generate() {
        Map<String, String> code = getAreaCode();
        String areaCode = code.keySet().toArray(new String[0])[RandomUtils
                .nextInt(0, code.size())]
                + StringUtils.leftPad((RandomUtils.nextInt(0, 9998) + 1) + "", 4,
                "0");
        System.out.println("areaCode: " + areaCode);
        String birthday = new SimpleDateFormat("yyyyMMdd").format(randomDate());
        String randomCode = String.valueOf(1000 + RandomUtils.nextInt(0, 999))
                .substring(1);
        System.out.println("randomCode: " + randomCode);
        String pre = areaCode + birthday + randomCode;
        String verifyCode = getVerifyCode(pre);
        String result = pre + verifyCode;

        return result;
    }

    /**
     * @param birthDate
     * @param sex       1为男，2为女
     * @return
     */
    public String generateByBirthDateAndSex(Date birthDate, Integer sex) {
        Map<String, String> code = getAreaCode();
        String areaCode = code.keySet().toArray(new String[0])[RandomUtils
                .nextInt(0, code.size())]
                + StringUtils.leftPad((RandomUtils.nextInt(0, 9998) + 1) + "", 4,
                "0");
        System.out.println("areaCode: " + areaCode);
        String randomCode = String.valueOf(1000 + RandomUtils.nextInt(0, 999))
                .substring(1);
        Integer randomCodeInt = Integer.valueOf(randomCode);
        System.out.println("randomCode: " + randomCode);
        if (sex == 1) {
            if (randomCodeInt % 2 == 0) {
                randomCodeInt += 1;
            }
        } else if (sex == 2) {
            if (randomCodeInt % 2 != 0) {
                randomCodeInt += 1;
            }
        }
        String birthday = new SimpleDateFormat("yyyyMMdd").format(birthDate);
        String pre = areaCode + birthday + StringUtils.leftPad(randomCodeInt + "", 4,
                "0");
        String verifyCode = getVerifyCode(pre);
        String result = pre + verifyCode;

        return result;
    }

    static Date randomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(DateUtil.getYearByDate(new Date()) - 1, 1, 1);
        long earlierDate = calendar.getTime().getTime();
        calendar.set(DateUtil.getYearByDate(new Date()) - 1, 12, 30);
        long laterDate = calendar.getTime().getTime();

        long chosenDate = RandomUtils.nextLong(earlierDate, laterDate);

        return new Date(chosenDate);
    }

    private static String getVerifyCode(String cardId) {
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        int tmp = 0;
        for (int i = 0; i < Wi.length; i++) {
            tmp += Integer.parseInt(String.valueOf(cardId.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }

        int modValue = tmp % 11;
        String strVerifyCode = ValCodeArr[modValue];

        return strVerifyCode;
    }

    private static Map<String, String> getAreaCode() {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");

        return map;
    }
}
