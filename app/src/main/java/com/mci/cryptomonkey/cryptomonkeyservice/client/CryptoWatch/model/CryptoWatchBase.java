package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CryptoWatchBase {
    int id;
    String symbol;
    String name;
    boolean fiat;
    String route;
}
