package com.mci.cryptomonkey.cryptomonkeyservice.runner.CryptoWatchRunner;

import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CryptoWatchRunner implements QuoteRunner {
    @Autowired
    @Qualifier("CryptoWatchDataClient")
    ExternalCryptoDataClient cryptoDataClient;

    @Override
    public void activate(long pollingRate) {

    }

    @Override
    public void terminate() {

    }
}
