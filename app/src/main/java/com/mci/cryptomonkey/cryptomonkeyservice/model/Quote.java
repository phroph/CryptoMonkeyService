package com.mci.cryptomonkey.cryptomonkeyservice.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Quote {
    private String quoteId;
    private int price;

    private String rank;
}
