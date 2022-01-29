package com.mci.cryptomonkey.cryptomonkeyservice.utilities;

public final class QuoteIdFormatter {
    private final static String QUOTE_ID_FORMAT = "%s-%s";

    public static String ConvertPairToQuoteId(String base, String currency) {
        return String.format(QUOTE_ID_FORMAT, base, currency);
    }
}
