package com.mci.cryptomonkey.cryptomonkeyservice.client;

import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalQuoteDatastore implements QuoteDatastoreClient {
    List<String> quoteIdList;

    Map<String, List<Quote>> quoteHistory;

    public LocalQuoteDatastore() {
        quoteIdList = new ArrayList<>();
        quoteHistory = new ConcurrentHashMap<>();
    }

    public synchronized void updateQuoteIds(List<String> ids) {
        quoteIdList.clear();
        quoteIdList.addAll(ids);
    }

    @Override
    public List<String> getQuoteIds() {
        return quoteIdList;
    }

    @Override
    public Quote getCurrentQuote(String quoteId, boolean includeRank) {
        List<Quote> quoteList = getQuoteHistory(quoteId);
        return quoteList.get(quoteList.size() - 1);
    }

    @Override
    public List<Quote> getQuoteHistory(String quoteId) {
        return quoteHistory.get(quoteId);
    }

    @Override
    public void updateQuote(Quote newQuote) {
        if (!quoteHistory.containsKey(newQuote.getQuoteId())) {
            quoteHistory.put(newQuote.getQuoteId(), new ArrayList<>());
        }
        quoteHistory.get(newQuote.getQuoteId()).add(newQuote);
    }
}
