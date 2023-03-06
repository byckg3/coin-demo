package demo.coin.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoindeskControllerTest
{
    private static CoindeskController coindesk;
    private static Map< String, String > codesToNames;

    @BeforeAll
    static void setUp()
    {
        codesToNames = new HashMap<>();
        codesToNames.put( "USD", "美元" );
        codesToNames.put( "EUR", "歐元" );
        codesToNames.put( "GBP", "英鎊" );

        coindesk = new CoindeskController( codesToNames );
    }

    @Test
    void currencyInfoShouldBeEqualToExpectedValues()
    {
        Map< String, String > eurInfo = ( Map ) coindesk.info().get( "EUR" );
        
        assertEquals( "EUR", eurInfo.get( "code" ) );
        assertEquals( "歐元", eurInfo.get( "name" ) );
        assertNotNull( eurInfo.get( "rate" ) );
    }

    @Test
    void testInputDateFormat()
    {
        // to do
    }
}
