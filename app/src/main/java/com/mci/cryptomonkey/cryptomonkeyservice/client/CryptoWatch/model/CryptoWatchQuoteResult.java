package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoWatchQuoteResult {
    int id;
    String symbol;
    CryptoWatchQuote quote;
    CryptoWatchBase base;
    String route;
}
