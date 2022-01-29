package com.mci.cryptomonkey.cryptomonkeyservice.activity.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCollectedMetricNamesResponse {
    List<String> metrics;
}
