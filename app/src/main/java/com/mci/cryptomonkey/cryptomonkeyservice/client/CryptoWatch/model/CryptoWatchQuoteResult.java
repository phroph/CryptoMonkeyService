package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CryptoWatchQuoteResult {
    int id;
    String symbol;
    CryptoWatchQuote quote;
    CryptoWatchBase base;
    String route;
}
