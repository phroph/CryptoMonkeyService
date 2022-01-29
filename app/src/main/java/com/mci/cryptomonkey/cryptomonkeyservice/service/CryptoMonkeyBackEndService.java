package com.mci.cryptomonkey.cryptomonkeyservice.service;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyBackEndServiceActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Service
@Path("/serve")
public class CryptoMonkeyBackEndService {
    @Autowired
    CryptoMonkeyBackEndServiceActivity activity;

    @GET
    @Path("quote/collectedMetricNames")
    @Produces("application/json")
    public String getCollectedMetricNames() {
        return activity.getCollectedMetricNames();
    }

    @GET
    @Path("quote/{coin}/{currency}")
    public String queryQuoteMetric(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        return activity.queryQuoteMetric(coin, currency);
    }
    @GET
    @Path("quote/{coin}/{currency}/rank")
    public String queryQuoteRank(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        return activity.queryQuoteRank(coin, currency);
    }
}
