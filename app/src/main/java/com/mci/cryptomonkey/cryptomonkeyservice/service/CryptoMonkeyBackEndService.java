package com.mci.cryptomonkey.cryptomonkeyservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mci.cryptomonkey.cryptomonkeyservice.activity.CryptoMonkeyBackEndServiceActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Service
//@RestController Should not be consumed publically
public class CryptoMonkeyBackEndService {
    @Autowired
    CryptoMonkeyBackEndServiceActivity activity;

    ObjectMapper mapper;

    public CryptoMonkeyBackEndService() {
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/serve/quote", method = RequestMethod.GET, produces = "application/json")
    public String getCollectedMetricNames() throws CryptoMonkeyInteralServiceError {
        try {
            return mapper.writeValueAsString(activity.getCollectedMetricNames());
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }

    @RequestMapping(value = "/serve/quote/{coin}/{currency}", method = RequestMethod.GET, produces = "application/json")
    public String queryQuoteMetric(
            @PathVariable("coin") String coin,
            @PathVariable("currency") String currency) throws CryptoMonkeyInteralServiceError {
        try {
            return  mapper.writeValueAsString(activity.queryQuoteMetric(coin, currency));
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }

    @RequestMapping(value = "/serve/quote/{coin}/{currency}/rank", method = RequestMethod.GET, produces = "application/json")
    public String queryQuoteRank(
            @PathVariable("coin") String coin,
            @PathVariable("currency") String currency) throws CryptoMonkeyInteralServiceError {
        try {
            return  mapper.writeValueAsString(activity.queryQuoteRank(coin, currency));
        } catch (JsonProcessingException e) {
            throw new CryptoMonkeyInteralServiceError(e);
        }
    }
}
