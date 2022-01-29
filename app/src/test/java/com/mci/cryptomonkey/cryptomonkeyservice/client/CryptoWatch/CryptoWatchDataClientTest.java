package com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch;

import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.CryptoWatchBase;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.CryptoWatchQuote;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.CryptoWatchQuoteResult;
import com.mci.cryptomonkey.cryptomonkeyservice.client.CryptoWatch.model.ListQuotesResponse;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CryptoWatchDataClientTest {
    @Mock
    Client mockClient;

    @Mock
    WebTarget mockRootTarget;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebTarget mockSubTarget;

    @Mock
    ListQuotesResponse mockListQuotesResponse;

    @Mock
    CryptoWatchQuoteResult mockQuoteResult;
    @Mock
    CryptoWatchBase mockBase;
    @Mock
    CryptoWatchQuote mockQuote;

    CryptoWatchDataClient inTest;

    private AutoCloseable closeableMocks;

    @BeforeEach
    public void setup() {
        closeableMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeableMocks.close();
    }

    @Test
    public void retrieveQuotesHappyPath() {
        when(mockClient.target(anyString())).thenReturn(mockRootTarget);
        when(mockRootTarget.path(anyString())).thenReturn(mockSubTarget);
        when(mockSubTarget.request(eq(MediaType.APPLICATION_JSON)).buildGet().invoke().readEntity(eq(ListQuotesResponse.class))).thenReturn(mockListQuotesResponse);
        when(mockListQuotesResponse.getResult()).thenReturn(Collections.singletonList(mockQuoteResult));
        when(mockQuoteResult.getQuote()).thenReturn(mockQuote);
        when(mockQuoteResult.getBase()).thenReturn(mockBase);
        when(mockBase.getSymbol()).thenReturn("test1");
        when(mockQuote.getSymbol()).thenReturn("test2");

        inTest = new CryptoWatchDataClient(mockClient);
        List<String> test = inTest.retrieveAvailableQuoteIds();
        Assertions.assertEquals(1, test.size());
        Assertions.assertEquals("test1-test2", test.get(0));
    }
}
