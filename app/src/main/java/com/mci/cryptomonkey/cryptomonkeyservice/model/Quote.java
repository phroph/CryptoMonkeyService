package com.mci.cryptomonkey.cryptomonkeyservice.model;

import lombok.Builder;

import java.util.Currency;

@Builder
public class Quote {
    private String quoteId;
    private Currency price;

    private String rank;
}
