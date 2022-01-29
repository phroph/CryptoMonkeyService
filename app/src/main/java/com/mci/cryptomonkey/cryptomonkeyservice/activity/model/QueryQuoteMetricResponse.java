package com.mci.cryptomonkey.cryptomonkeyservice.activity.model;

import com.mci.cryptomonkey.cryptomonkeyservice.model.Quote;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryQuoteMetricResponse {
    Quote quote;
}
