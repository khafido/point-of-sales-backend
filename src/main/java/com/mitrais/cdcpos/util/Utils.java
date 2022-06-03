package com.mitrais.cdcpos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static BigDecimal calculateItemBySystemPrice(int taxPercentage, int profitPercentage, BigDecimal pricePerItem) {
        // bySystemPrice Formula: f(pricePerItem + (profit% * pricePerItem)) + (f() * tax%)
        BigDecimal bySystemPrice;
        BigDecimal profitValue = pricePerItem.multiply(new BigDecimal((double) taxPercentage / 100));
        BigDecimal buyPlusProfit = pricePerItem.add(profitValue);
        bySystemPrice = buyPlusProfit.add(buyPlusProfit.multiply(new BigDecimal((double) profitPercentage / 100)));
        bySystemPrice = bySystemPrice.setScale(3, RoundingMode.HALF_UP);
        return bySystemPrice;
    }
}
