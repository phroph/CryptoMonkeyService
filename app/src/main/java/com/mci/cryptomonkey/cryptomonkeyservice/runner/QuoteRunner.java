package com.mci.cryptomonkey.cryptomonkeyservice.runner;

public interface QuoteRunner {
    /**
     * Activates the runner to start polling from a quote source.
     * @param pollingRate how often (in milliseconds) that the runner should poll.
     */
    void activate(long pollingRate);

    /**
     * Terminates the runner, stopping all data updates and polling.
     */
    void terminate();

    boolean isRunning();
}
