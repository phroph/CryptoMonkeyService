package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoWatchBase {
    int id;
    String symbol;
    String name;
    boolean fiat;
    String route;
}
