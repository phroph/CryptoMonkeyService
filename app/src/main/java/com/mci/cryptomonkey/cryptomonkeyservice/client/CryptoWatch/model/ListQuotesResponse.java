package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ListQuotesResponse {
    private List<CryptoWatchQuoteResult> result;
}
