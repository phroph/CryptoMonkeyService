package com.mci.cryptomonkey.cryptomonkeyservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    ObjectMapper mapper;

    public CryptoMonkeyBackEndService() {
        mapper = new ObjectMapper();
    }

    @GET
    @Path("quote/collectedMetricNames")
    @Produces("application/json")
    public String getCollectedMetricNames() throws CryptoMonkeyInteralServiceError {
        try {
            return mapper.writeValueAsString(activity.getCollectedMetricNames());
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }

    @GET
    @Path("quote/{coin}/{currency}")
    public String queryQuoteMetric(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) throws CryptoMonkeyInteralServiceError {
        try {
            return  mapper.writeValueAsString(activity.queryQuoteMetric(coin, currency));
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }

    @GET
    @Path("quote/{coin}/{currency}/rank")
    public String queryQuoteRank(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) throws CryptoMonkeyInteralServiceError {
        try {
            return  mapper.writeValueAsString(activity.queryQuoteRank(coin, currency));
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }
}
