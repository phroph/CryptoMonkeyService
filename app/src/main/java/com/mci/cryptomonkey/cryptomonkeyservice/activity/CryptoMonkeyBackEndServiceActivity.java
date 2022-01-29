package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CryptoMonkeyBackEndServiceActivity {
    @Autowired
    @Qualifier("CryptoWatchRunner")
    QuoteRunner dataClient;

    @Autowired
    @Qualifier("DynamoQuoteStoreClient")
    QuoteDatastoreClient quoteClient;

    public String getCollectedMetricNames() {
        throw new NotImplementedException();
    }

    public String queryQuoteMetric(String coin, String currency) {
        throw new NotImplementedException();
    }

    public String queryQuoteRank(String coin, String currency) {
        throw new NotImplementedException();
    }
}
