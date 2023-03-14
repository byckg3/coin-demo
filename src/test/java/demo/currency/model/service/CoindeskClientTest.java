package demo.currency.model.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RestClientTest()
public class CoindeskClientTest
{
    
    private CoindeskClient client;
    private Map< String, String > codesToNames;

    // @Autowired
    // private RestTemplateBuilder builder;

    // @Autowired
    // private MockRestServiceServer server;

    // @Autowired
    // private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        codesToNames = new HashMap<>();
        codesToNames.put( "USD", "美元" );
        codesToNames.put( "EUR", "歐元" );
        codesToNames.put( "GBP", "英鎊" );

        client = new CoindeskClient( new RestTemplateBuilder(), codesToNames );
    }

    @Test
    void callGetCurrentPrice_shouldReturnExpectedString() throws JsonProcessingException
    {
        Map< String, Object > coindeskResponse = client.getCurrentPrice();
        
        

        System.out.println( coindeskResponse );
    }

    @Test
    void callGetCurrencyInfo_shouldReturnExpectedValues() throws JsonProcessingException
    {
        Map< String, String > eurInfo = ( Map ) client.getCurrentInfo().get( "EUR" );
        
        assertEquals( "EUR", eurInfo.get( "code" ) );
        assertEquals( "歐元", eurInfo.get( "name" ) );
        assertNotNull( eurInfo.get( "rate" ) );

        System.out.println( eurInfo );
    }
}
