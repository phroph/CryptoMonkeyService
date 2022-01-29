package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import com.mci.cryptomonkey.cryptomonkeyservice.service.CryptoMonkeyBackEndService;
import com.mci.cryptomonkey.cryptomonkeyservice.service.CryptoMonkeyInteralServiceError;
import org.springframework.beans.factory.annotation.Autowired;

public class CryptoMonkeyFrontEndServiceActivity {
    @Autowired
    CryptoMonkeyBackEndService backEndService;

    public String getAvailableQuotes() throws CryptoMonkeyInteralServiceError {
        return backEndService.getCollectedMetricNames();
    }

    public String getQuote(String coin, String currency) throws CryptoMonkeyInteralServiceError {
        return backEndService.queryQuoteMetric(coin, currency);
    }

    public String getRank(String coin, String currency) throws CryptoMonkeyInteralServiceError {
        return backEndService.queryQuoteRank(coin, currency);
    }
}
