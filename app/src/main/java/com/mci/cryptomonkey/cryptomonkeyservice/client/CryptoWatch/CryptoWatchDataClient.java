package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch;

import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;

import java.util.List;

public class CryptoWatchDataClient implements ExternalCryptoDataClient {
    @Override
    public List<String> retrieveAvailableQuoteIds() {
        return null;
    }

    @Override
    public List<Quote> retrieveQuote(String coin, String currency) {
        return null;
    }
}
