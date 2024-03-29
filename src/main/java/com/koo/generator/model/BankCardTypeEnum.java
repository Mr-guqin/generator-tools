package com.koo.generator.model;

/**
 * @author gu.wq
 * @version 1.0
 * @type BankCardTypeEnum
 * @desc
 * @date 2024/3/22 9:48
 */
public enum BankCardTypeEnum {

    /**
     * 借记卡/储蓄卡
     */
    DEBIT("借记卡/储蓄卡"),
    /**
     * 信用卡/贷记卡
     */
    CREDIT("信用卡/贷记卡");

    private final String name;

    BankCardTypeEnum(String name) {
        this.name = name;
    }
}
