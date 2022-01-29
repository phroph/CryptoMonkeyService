package com.mci.cryptomonkey.cryptomonkeyservice;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyBackEndServiceActivity;
import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyFrontEndServiceActivity;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.CryptoWatchDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.DynamoQuoteStore.DynamoQuoteStoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.LocalQuoteDatastore;
import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.CryptoWatchRunner.CryptoWatchRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.service.CryptoMonkeyBackEndService;
import com.mci.cryptomonkey.cryptomonkeyservice.service.CryptoMonkeyFrontEndService;
import org.glassfish.jersey.server.ResourceConfig;
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

    @Bean
    public CryptoMonkeyFrontEndServiceActivity frontEndServiceActivity() {
        return new CryptoMonkeyFrontEndServiceActivity();
    }
    @Bean
    public CryptoMonkeyBackEndServiceActivity backEndServiceActivity() {
        return new CryptoMonkeyBackEndServiceActivity();
    }
    @Bean
    public CryptoMonkeyFrontEndService frontEndService() {
        return new CryptoMonkeyFrontEndService();
    }
    @Bean
    public CryptoMonkeyBackEndService backEndService() {
        return new CryptoMonkeyBackEndService();
    }

    @Bean(name = "LocalQuoteDatastore")
    public LocalQuoteDatastore localQuoteDatastore() {
        return new LocalQuoteDatastore();
    }
}
