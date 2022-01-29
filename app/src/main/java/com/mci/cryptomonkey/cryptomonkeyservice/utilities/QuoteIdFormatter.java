package com.mci.cryptomonkey.cryptomonkeyservice.utilities;

import javafx.util.Pair;

import java.security.InvalidParameterException;

public final class QuoteIdFormatter {
    private final static String DELIMITER = "-";
    private final static String QUOTE_ID_FORMAT = "%s" + DELIMITER + "%s";

    public static String ConvertPairToQuoteId(String base, String currency) {
        return String.format(QUOTE_ID_FORMAT, base, currency);
    }

    public static Pair<String,String> ConvertQuoteIdToPair(String quoteId) {
        String[] quoteComponents = quoteId.split(DELIMITER);
        if(quoteComponents.length != 2) {
            throw new InvalidParameterException("Invalid QuoteId");
        }
        return new Pair<>(quoteComponents[0], quoteComponents[1]);
    }
}
