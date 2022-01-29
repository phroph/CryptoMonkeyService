package com.mci.cryptomonkey.cryptomonkeyservice.activity;

import com.mci.cryptomonkey.cryptomonkeyservice.service.CryptoMonkeyBackEndService;
import org.springframework.beans.factory.annotation.Autowired;

public class CryptoMonkeyFrontEndServiceActivity {
    @Autowired
    CryptoMonkeyBackEndService backEndService;

    public String getAvailableQuotes() {
        return backEndService.getCollectedMetricNames();
    }

    public String getQuote(String coin, String currency) {
        return backEndService.queryQuoteMetric(coin, currency);
    }

    public String getRank(String coin, String currency) {
        return backEndService.queryQuoteRank(coin, currency);
    }
}
