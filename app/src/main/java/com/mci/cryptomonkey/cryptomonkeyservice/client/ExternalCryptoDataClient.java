package com.mci.cryptomonkey.cryptomonkeyservice.client;

import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;

import java.util.List;

public interface ExternalCryptoDataClient {

    /**
     * Retrieves all quote Ids (coin/currency pairs) know by the underlying datasource.
     * @return all quote Ids found
     */
    List<String> retrieveAvailableQuoteIds();

    /**
     * Retrieves the quote for a given coin/currency pair.
     * @return
     */
    List<Quote> retrieveQuote(String coin, String currency);
}
