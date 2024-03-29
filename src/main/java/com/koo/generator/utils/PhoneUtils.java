package com.koo.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gu.wq
 * @version 1.0
 * @type PhoneUtils
 * @desc
 * @date 2024/3/21 21:42
 */
public class PhoneUtils {

    /**
     * 生成手机号
     *
     * */
    public static String generatorPhoneNo(){
        String haoduan = haoDuan((int)((Math.random()*34)+1));
        int max = 99999999;
        int min = 10000000;
        int num = (int)(((Math.random()*(max-min))+min));
        return haoduan+""+num;
    }

    /*号段*/
    public static String haoDuan(int num){
        String[] hd ={"130","131","132","133","134","135","136","137","138","139","145","147","150","151","152","153","156","157","158","159","170","176","177","178","180","181","182","183","184","185","186","187","188","189"};
        return hd[num-1];
    }

    /*校验*/
    public static boolean checkMobile(String phone) {
        String regu = "/^1[3|4|5|7|8][0-9]\\d{8}$/";
        Pattern pattern = Pattern.compile(regu);
        Matcher match = pattern.matcher(phone);
        return match.matches();
    }
}
