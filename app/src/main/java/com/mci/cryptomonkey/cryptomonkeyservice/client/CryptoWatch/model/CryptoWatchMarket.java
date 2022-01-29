package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoWatchMarket {
    int id;
    String exchange;
    String pair;
    boolean active;
    String route;
}
