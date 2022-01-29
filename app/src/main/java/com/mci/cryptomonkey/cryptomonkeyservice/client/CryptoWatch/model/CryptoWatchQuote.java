package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CryptoWatchQuote {
    int id;
    String symbol;
    String name;
    boolean fiat;
    String route;
    List<CryptoWatchMarket> markets;
}
