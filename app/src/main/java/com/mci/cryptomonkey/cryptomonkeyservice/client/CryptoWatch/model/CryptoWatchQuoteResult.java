package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoWatchQuoteResult {
    int id;
    String symbol;
    CryptoWatchQuote quote;
    CryptoWatchBase base;
    String route;
}
