package com.mci.cryptomonkey.cryptomonkeyservice.service;

import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyFrontEndServiceActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
public class CryptoMonkeyFrontEndService {
    @Autowired
    CryptoMonkeyFrontEndServiceActivity activity;

    @RequestMapping(value = "/query/quote", method = RequestMethod.GET, produces = "application/json")
    public String getAvailableQuotes() throws CryptoMonkeyInteralServiceError {
        return activity.getAvailableQuotes();
    }

    @RequestMapping(value = "/query/quote/{coin}/{currency}", method = RequestMethod.GET, produces = "application/json")
    public String getQuote(
            @PathVariable("coin") String coin,
            @PathVariable("currency") String currency) throws CryptoMonkeyInteralServiceError {
        return activity.getQuote(coin, currency);
    }

    @RequestMapping(value = "/query/quote/{coin}/{currency}/rank", method = RequestMethod.GET, produces = "application/json")
    public String getRank(
            @PathVariable("coin") String coin,
            @PathVariable("currency") String currency) throws CryptoMonkeyInteralServiceError {
        return activity.getRank(coin, currency);
    }
}
