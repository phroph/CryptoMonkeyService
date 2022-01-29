package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.CryptowatchPriceResult;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.ListQuotesResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.PriceResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.RetrieveQuoteResponse;
import com.mci.cryptomonkey.cryptomonkeyservice.client.ExternalCryptoDataClient;
import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;
import com.mci.cryptomonkey.cryptomonkeyservice.utilities.QuoteIdFormatter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class CryptoWatchDataClient implements ExternalCryptoDataClient {
    WebTarget rootTarget;
    WebTarget listQuotesTarget;

    public CryptoWatchDataClient(Client client)
    {
        rootTarget = client.target("https://api.cryptowat.ch/");
        listQuotesTarget = rootTarget.path("pairs");
    }

    public CryptoWatchDataClient() {
        this(ClientBuilder.newClient());
    }

    @Override
    public List<String> retrieveAvailableQuoteIds() {
        Response response = listQuotesTarget.request(MediaType.APPLICATION_JSON).buildGet().invoke();
        ListQuotesResponse decodedResponse = response.readEntity(ListQuotesResponse.class);
        return decodedResponse.getResult().stream().map(pair ->
                        QuoteIdFormatter.ConvertPairToQuoteId(pair.getBase().getSymbol(), pair.getQuote().getSymbol()))
                .collect(Collectors.toList());
    }

    @Override
    public Quote retrieveQuote(String coin, String currency) {
        // TO-DO: Add per market support.
        String pair = coin + currency;
        RetrieveQuoteResponse response = rootTarget.path("pairs/{pair}").resolveTemplate("pair", pair)
                .request(MediaType.APPLICATION_JSON).buildGet().invoke()
                .readEntity(RetrieveQuoteResponse.class);
        if (response.getResult().getMarkets().size() > 0) {
            CryptowatchPriceResult price =
                    retrieveQuotePrice(pair, response.getResult().getMarkets().get(0).getExchange());
            Quote quote = Quote.builder()
                    .quoteId(QuoteIdFormatter.ConvertPairToQuoteId(coin, currency))
                    .price(price.getPrice())
                    .build();
            return quote;
        }

        return null;
    }

    private CryptowatchPriceResult retrieveQuotePrice(String pair, String market) {
        PriceResponse response = rootTarget.path("markets/{exchange}/{pair}")
                .resolveTemplate("exchange", market)
                .resolveTemplate("pair", pair)
                .request(MediaType.APPLICATION_JSON).buildGet().invoke()
                .readEntity(PriceResponse.class);
        return response.getResult();
    }
}
