package demo.currency.model.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoindeskClient
{
    private Map< String, String > namesByCodes;
    private RestTemplate restTemplate;
    private final String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final String dateFormat = "yyyy/MM/dd HH:mm:ss";
    
    //@Autowired
    public CoindeskClient( RestTemplateBuilder restTemplateBuilder, Map< String, String > mappings )
    {
        this.restTemplate = restTemplateBuilder.build();
        this.namesByCodes = mappings;
    }

    public void setCodeToNameMappings( Map< String, String > mappings )
    {
        
        this.namesByCodes = mappings;
    }

    public Map< String, String > getCodeToNameMappings()
    {
        return this.namesByCodes;
    }

    public Map< String, Object > getCurrentPrice() throws JsonMappingException, JsonProcessingException
    {
        String jsonString = restTemplate.getForObject( url, String.class );
        return new ObjectMapper().readValue( jsonString, HashMap.class );
    }

    public Map< String, Object > getCurrentInfo() throws JsonProcessingException
    {
        Map< String, Object > coindeskMap = getCurrentPrice();
        JSONObject coindeskJSON = new JSONObject( coindeskMap );

        // fetch updated time
        String updatedDate = coindeskJSON.getJSONObject( "time" ).getString( "updatedISO" );
        Map< String, Object > info = new HashMap<>();
        info.put( "updatedISO", convertIsoDateFormat( updatedDate, dateFormat ) );

        // fetch currency code, rate and add its name
        Set< String > currencies = coindeskJSON.getJSONObject( "bpi" ).keySet();
        for ( String currency: currencies )
        {
            JSONObject currencyJSON = coindeskJSON.getJSONObject( "bpi" ).getJSONObject( currency );
            String currencyCode = currencyJSON.getString( "code" );

            Map< String, String > currencyItems = new HashMap<>();
            currencyItems.put( "code", currencyCode );
            currencyItems.put( "name", namesByCodes.getOrDefault( currencyCode, "" ) );
            currencyItems.put( "rate", currencyJSON.getString( "rate" ) );
            
            info.put( currency, currencyItems );
        }
        return info;
    }

    private String convertIsoDateFormat( String dateString, String pattern )
    {
        OffsetDateTime dateTime = OffsetDateTime.parse( dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME );
        return dateTime.format( DateTimeFormatter.ofPattern( pattern ) );
    }
}
