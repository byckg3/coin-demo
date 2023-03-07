package demo.currency.model.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoindeskClientTest
{
    private static CoindeskClient client;
    private static Map< String, String > codesToNames;

    @BeforeAll
    static void setUp()
    {
        codesToNames = new HashMap<>();
        codesToNames.put( "USD", "美元" );
        codesToNames.put( "EUR", "歐元" );
        codesToNames.put( "GBP", "英鎊" );

        client = new CoindeskClient( codesToNames );
    }

    @Test
    void currencyInfoShouldBeEqualToExpectedValues() throws JsonProcessingException
    {
        Map< String, String > eurInfo = ( Map ) client.getCurrentInfo().get( "EUR" );
        
        assertEquals( "EUR", eurInfo.get( "code" ) );
        assertEquals( "歐元", eurInfo.get( "name" ) );
        assertNotNull( eurInfo.get( "rate" ) );
    }
}
