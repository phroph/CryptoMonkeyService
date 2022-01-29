package com.mci.cryptomonkey.cryptomonkeyservice.runner.CryptoWatchRunner;

import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.LocalQuoteDatastore;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.utilities.QuoteIdFormatter;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CryptoWatchRunner implements QuoteRunner {
    @Autowired
    @Qualifier("CryptoWatchDataClient")
    ExternalCryptoDataClient cryptoDataClient;

    @Autowired
    LocalQuoteDatastore localQuoteDatastore;

    ScheduledExecutorService threadPool;

    boolean isActive = false;

    public CryptoWatchRunner() {
        threadPool = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void activate(long pollingRate) {
        threadPool.scheduleWithFixedDelay(() ->
                mainRunnable(cryptoDataClient), pollingRate, pollingRate, TimeUnit.MILLISECONDS);
        isActive = true;
    }

    private void mainRunnable(ExternalCryptoDataClient client) {
        List<String> quoteIdList = localQuoteDatastore.getQuoteIds();
        if (shouldRefreshQuoteList(quoteIdList)) {
            localQuoteDatastore.updateQuoteIds(client.retrieveAvailableQuoteIds());
        }
        quoteIdList.parallelStream().map(quoteId -> {
            Pair<String,String> quotePair = QuoteIdFormatter.ConvertQuoteIdToPair(quoteId);
            return client.retrieveQuote(quotePair.getKey(), quotePair.getValue());
        }).forEach(quote -> localQuoteDatastore.updateQuote(quote));
    }

    private boolean shouldRefreshQuoteList(List<String> quoteIdList) {
        return quoteIdList.size() == 0;
    }

    @Override
    public void terminate() {
        threadPool.shutdown();
        // TODO: terminate client resources and propagate disposal.
    }

    @Override
    public boolean isRunning() {
        return isActive; // TODO: fix this to be smarter in case runnner fails.
    }
}
