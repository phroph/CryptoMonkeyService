package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.model.GetCollectedMetricNamesResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.activity.model.QueryQuoteMetricResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.activity.model.QueryQuoteRankResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.utilities.QuoteIdFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CryptoMonkeyBackEndServiceActivity implements ApplicationListener<ApplicationReadyEvent> {
    private final static int POLLING_RATE = 1000;

    @Autowired
    @Qualifier("CryptoWatchRunner")
    QuoteRunner dataClient;

    @Autowired
    @Qualifier("LocalQuoteDatastore") // TODO: Implement and upgrade to durable storage like S3/DDB
    QuoteDatastoreClient quoteClient;

    public void initialize() {
        if(!dataClient.isRunning()) {
            dataClient.activate(POLLING_RATE);
        }
    }

    public GetCollectedMetricNamesResponse getCollectedMetricNames() {
        return GetCollectedMetricNamesResponse.builder()
                .metrics(quoteClient.getQuoteIds())
                .build();
    }

    public QueryQuoteMetricResponse queryQuoteMetric(String coin, String currency) {
        return QueryQuoteMetricResponse.builder()
                .quote(quoteClient
                        .getCurrentQuote(QuoteIdFormatter.ConvertPairToQuoteId(coin, currency), false))
                .build();
    }

    public QueryQuoteRankResponse queryQuoteRank(String coin, String currency) {
        // TODO: Need to implement ranking
        return QueryQuoteRankResponse.builder()
                .quote(quoteClient
                        .getCurrentQuote(QuoteIdFormatter.ConvertPairToQuoteId(coin, currency), true))
                .build();
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initialize();
    }
}
