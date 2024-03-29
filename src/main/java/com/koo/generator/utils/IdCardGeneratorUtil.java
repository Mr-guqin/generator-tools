package com.koo.generator.utils;

import com.koo.generator.generator.ChineseIDCardNumberGenerator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gu.wq
 * @version 1.0
 * @type IdCardUtil
 * @desc
 * @date 2024/3/21 14:27
 */
public class IdCardGeneratorUtil {

    public static void main(String[] args) {
        System.out.println();
        System.out.println(ChineseIDCardNumberGenerator.generateValidPeriod());
        System.out.println(ChineseIDCardNumberGenerator.getInstance().generate());
        generatorPersonCard("zhangsan", ChineseIDCardNumberGenerator.getInstance().generateByBirthDateAndSex(new Date(), 1), ChineseIDCardNumberGenerator.generateIssueOrg());
        generatorNationalCard("zhangsan", ChineseIDCardNumberGenerator.generateIssueOrg(), ChineseIDCardNumberGenerator.generateValidPeriod());
    }

    public static String generatorPersonCard(String personName, String idCardNo, String address) {
        IdCardUtil tt = new IdCardUtil();
        Map<String, String> result = new HashMap<>();
//        tt.defineIdno(result, "441424199512086036");
        tt.defineIdno(result, idCardNo);
        String name = personName;
//        String address = "新疆XX县XXXX镇XX社区XXX路XXXX苑一期XX号楼X单元XXX室";
        //生成文件名
        String fileName = name + "-人脸面.jpg";
        String sex = result.get("sex");
        BufferedImage d;
        if ("男".equals(sex)) {
            d = tt.loadImageUrl(ClassLoader.getSystemResource("idz_nan1.jpg"));
        } else {
            d = tt.loadImageUrl(ClassLoader.getSystemResource("idz_nv4.jpg"));
        }

        //往图片上写文字
        //姓名
        tt.writeImageLocal(fileName, tt.modifyImage(d, name, 95, 48, 0));
        //性别
        tt.writeImageLocal(fileName, tt.modifyImage(d, result.get("sex"), 95, 95, 0));
        //民族
        tt.writeImageLocal(fileName, tt.modifyImage(d, "汉", 220, 95, 0));
        //出生年月
        tt.writeImageLocal(fileName, tt.modifyImage(d, result.get("year") + "    " + result.get("month") + "    " + result.get("day"), 120, 135, 0));

        List<String> data = new ArrayList<String>();
        int iLen = address.length();
        if (iLen < 20) {
            data.add(address);
        }
        while (iLen >= 20) {
            String tmp = address.substring(0, 20);
            data.add(tmp);
            address = address.substring(20);
            iLen = address.length();
        }
        for (int i = 0; i < data.size(); i++) {
            //住址
            tt.writeImageLocal(fileName, tt.modifyImage(d, data.get(i), 95, 181 + 10 * i, 0));
        }
        //身份证号
        tt.writeImageLocal(fileName, tt.modifyImage(d, result.get("idCardNo"), 180, 292, 10));
        System.out.println("身份证正面生成成功~！");
        return fileName;
    }

    /**
     * 国徽面
     *
     * @param personName
     * @param police
     */
    public static String generatorNationalCard(String personName, String police, String userLifes) {
        IdCardUtil tt = new IdCardUtil();
        //生成文件名
        String fileName = personName + "-国徽面.jpg";
        BufferedImage d = tt.loadImageUrl(ClassLoader.getSystemResource("idf.jpg"));
        String userLifeb = userLifes.split("-")[0];
        String userLifee = userLifes.split("-")[1];
        StringBuffer sb1 = new StringBuffer();
        if ("永久".equals(userLifee)) {
            sb1.append(userLifeb).insert(4, ".").insert(7, ".").append("-").append(userLifee);
        } else {
            sb1.append(userLifeb).insert(4, ".").insert(7, ".").append("-").append(userLifee).insert(15, ".").insert(18, ".");
        }
        //往图片上写文字
        tt.writeImageLocal(fileName, tt.modifyImage(d, police, 240, 263, 0)); //签发机关
        tt.writeImageLocal(fileName, tt.modifyImage(d, sb1.toString(), 240, 307, 0)); //有效期限
        System.out.println("身份证反面生成成功~！");
        return fileName;
    }
}
