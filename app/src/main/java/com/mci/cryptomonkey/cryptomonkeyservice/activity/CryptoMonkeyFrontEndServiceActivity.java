package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Service
@Path("/query")
public class CryptoMonkeyFrontEndServiceActivity {
    @GET
    @Path("quote")
    @Produces("application/json")
    public String getAvailableQuotes() {
        throw new NotImplementedException();
    }

    @GET
    @Path("quote/{coin}/{currency}")
    public String getQuote(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        throw new NotImplementedException();
    }
    @GET
    @Path("quote/{coin}/{currency}/rank")
    public String getRank(
            @PathParam("coin") String coin,
            @PathParam("currency") String currency) {
        throw new NotImplementedException();
    }
}
