package com.koo.generator.utils;

import java.awt.image.BufferedImage;

/**
 * @author gu.wq
 * @version 1.0
 * @type IDCardReverse
 * @desc
 * @date 2024/3/21 14:33
 */
public class IDCardReverse {
    /**
     * 身份证反面
     *
     * @param args
     */
    public static void main(String[] args) {
        IdCardUtil tt = new IdCardUtil();
        String name = "雷军";
        String fileName = "E:\\idcard\\" + name + "Back8.jpg"; //生成文件名
        BufferedImage d = tt.loadImageUrl(ClassLoader.getSystemResource("idf.jpg"));
        String police = "伊宁县公安局";
        String userlifeb = "20210329";
        String userlifee = "永久";
        StringBuffer sb1 = new StringBuffer();
        if ("永久".equals(userlifee)) {
            sb1.append(userlifeb).insert(4, ".").insert(7, ".").append("-").append(userlifee);
        } else {
            sb1.append(userlifeb).insert(4, ".").insert(7, ".").append("-").append(userlifee).insert(15, ".").insert(18, ".");
        }
        //往图片上写文字
        tt.writeImageLocal(fileName, tt.modifyImage(d, police, 240, 263, 0)); //签发机关
        tt.writeImageLocal(fileName, tt.modifyImage(d, sb1.toString(), 240, 307, 0)); //有效期限
        System.out.println("身份证反面生成成功~！");
    }
}
