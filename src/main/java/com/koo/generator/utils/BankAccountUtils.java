package com.koo.generator.utils;

import com.koo.generator.generator.BankCardNumberGenerator;
import com.koo.generator.model.BankCardTypeEnum;
import com.koo.generator.model.BankNameEnum;

/**
 * @author gu.wq
 * @version 1.0
 * @type BankAccountUtils
 * @desc
 * @date 2024/3/21 21:25
 */
public class BankAccountUtils {

    public static String generatorBankAccount(BankNameEnum bankName) {
        return BankCardNumberGenerator.generate(bankName, BankCardTypeEnum.CREDIT);
    }

    public static String generatorBankAccount() {
        return BankCardNumberGenerator.getInstance().generate();
    }
}
