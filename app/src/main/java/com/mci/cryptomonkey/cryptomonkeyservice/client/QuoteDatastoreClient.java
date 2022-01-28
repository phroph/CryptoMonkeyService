package com.mci.cryptomonkey.cryptomonkeyservice.client;

import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;

import java.util.List;

public interface QuoteDatastoreClient {
    /**
     * Gets the list of all Quote ids known by the datastore
     * @return
     */
    List<String> getQuoteIds();

    /**
     * Returns the current quote for a given quoteId known by the datastore
     * @param quoteId The QuoteID (coin-currency) to lookup
     * @param includeRank Whether to include percentile rank in the result
     * @return Returns information known about the quote
     */
    Quote getCurrentQuote(String quoteId, boolean includeRank);

    /**
     * Returns the historical quotes for a given quoteId
     * @param quoteId The QuoteID (coin-currency) to lookup
     * @return
     */
    List<Quote> getQuoteHistory(String quoteId);

    /**
     * Adds a new quote record, updating the associated quoteId
     * @param newQuote New quote record for an associated quoteId
     */
    void updateQuote(Quote newQuote);
}
