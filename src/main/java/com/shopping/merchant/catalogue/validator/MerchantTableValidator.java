package com.shopping.merchant.catalogue.validator;


import com.shopping.merchant.catalogue.entity.Merchant;

public class MerchantTableValidator {
    public static boolean stringOnlyWithAlphabetAndSpaces(String data) {
        return data.matches("^[ A-Za-z]+$");
    }

    public static boolean alphaNumericCheck(String data) {
        return data.matches("[a-zA-Z0-9]+");
    }

    public static Integer sizeReportString(String str) {
        return str.length();
    }


    public static String validity(Merchant merchant) {
        String message = "";
        if (!stringOnlyWithAlphabetAndSpaces(merchant.getMerchantName()))
            message = message + "Invalid Name , ";
        if (!alphaNumericCheck(merchant.getGstin()) || sizeReportString(merchant.getGstin()) != 15)
            message = message + "Invalid GSTIN , ";
        if (!alphaNumericCheck(merchant.getPan()) || sizeReportString(merchant.getPan()) != 10)
            message = message + "Invalid PAN , ";
        if (!(sizeReportString(merchant.getAccountNumber().toString()) > 9) ||
                !(sizeReportString(merchant.getAccountNumber().toString()) < 16))
            message = message + "Invalid account Number , ";
        if (!(sizeReportString(merchant.getPhoneNumber().toString()) == 10))
            message = message + "Invalid Phone Number";
        return message;

    }
}
