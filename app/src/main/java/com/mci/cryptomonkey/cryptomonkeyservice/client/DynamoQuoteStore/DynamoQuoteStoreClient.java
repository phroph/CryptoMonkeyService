package com.mci.cryptomonkey.cryptomonkeyservice.client.DynamoQuoteStore;

import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;

import java.util.List;

public class DynamoQuoteStoreClient implements QuoteDatastoreClient {
    @Override
    public List<String> getQuoteIds() {
        return null;
    }

    @Override
    public Quote getCurrentQuote(String quoteId, boolean includeRank) {
        return null;
    }

    @Override
    public List<Quote> getQuoteHistory(String quoteId) {
        return null;
    }

    @Override
    public void updateQuote(Quote newQuote) {

    }
}
