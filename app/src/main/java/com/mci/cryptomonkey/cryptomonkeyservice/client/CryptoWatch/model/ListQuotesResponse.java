package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListQuotesResponse {
    private List<CryptoWatchQuoteResult> result;
}
