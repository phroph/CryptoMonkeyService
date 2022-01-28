package com.mci.cryptomonkey.cryptomonkeyservice;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyFrontEndServiceActivity;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.CryptoWatchDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.DynamoQuoteStore.DynamoQuoteStoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.CryptoWatchRunner.CryptoWatchRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(CryptoMonkeyFrontEndServiceActivity.class);
    }

    @Bean(name = "DynamoQuoteStoreClient")
    public QuoteDatastoreClient dynamoQuoteStoreClient() {
        return new DynamoQuoteStoreClient();
    }
    @Bean(name = "CryptoWatchRunner")
    public QuoteRunner cryptoWatchRunner() {
        return new CryptoWatchRunner();
    }

    @Bean(name = "CryptoWatchDataClient")
    public ExternalCryptoDataClient cryptoWatchDataClient() {
        return new CryptoWatchDataClient();
    }
}