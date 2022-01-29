package com.mci.cryptomonkey.cryptomonkeyservice.service;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyFrontEndServiceActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Service
@Path("/query")
public class CryptoMonkeyFrontEndService {
    @Autowired
    CryptoMonkeyFrontEndServiceActivity activity;

    @GET
    @Path("quote")
    @Produces("application/json")
    public String getAvailableQuotes() throws CryptoMonkeyInteralServiceError {
        return activity.getAvailableQuotes();
    }

    @GET
    @Path("quote/{coin}/{currency}")
    public String getQuote(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) throws CryptoMonkeyInteralServiceError {
        return activity.getQuote(coin, currency);
    }
    @GET
    @Path("quote/{coin}/{currency}/rank")
    public String getRank(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) throws CryptoMonkeyInteralServiceError {
        return activity.getRank(coin, currency);
    }
}
