package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PriceResponse {
    CryptowatchPriceResult result;
}
