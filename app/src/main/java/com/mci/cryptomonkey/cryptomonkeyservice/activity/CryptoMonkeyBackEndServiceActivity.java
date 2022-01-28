package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.client.QuoteDatastoreClient;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.CryptoWatchRunner.CryptoWatchRunner;
import com.mci.cryptomonkey.cryptomonkeyservice.runner.QuoteRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Service
@Path("/serve")
public class CryptoMonkeyBackEndServiceActivity {
    @Autowired
    @Qualifier("CryptoWatchRunner")
    QuoteRunner dataClient;

    @Autowired
    @Qualifier("DynamoQuoteStoreClient")
    QuoteDatastoreClient quoteClient;

    public CryptoMonkeyBackEndServiceActivity() {

    }

    @GET
    @Path("quote/collectedMetricNames")
    @Produces("application/json")
    public String getCollectedMetricNames() {
        throw new NotImplementedException();
    }

    @GET
    @Path("quote/{coin}/{currency}")
    public String queryQuoteMetric(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        throw new NotImplementedException();
    }
    @GET
    @Path("quote/{coin}/{currency}/rank")
    public String queryQuoteRank(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        throw new NotImplementedException();
    }
}
