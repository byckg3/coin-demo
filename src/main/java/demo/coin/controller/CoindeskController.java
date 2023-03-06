package demo.coin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/coindesk" )
@CrossOrigin( origins = "*" )
public class CoindeskController
{
    private Map< String, String > namesByCodes;
    private RestTemplate restTemplate;
    private final String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final String dateFormat = "yyyy/MM/dd HH:mm:ss";

    @Autowired
    public CoindeskController( Map< String, String > mappings )
    {
        this.namesByCodes = mappings;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping()
    public Map< String, Object > coindesk() throws JsonProcessingException
    {
        String jsonString = restTemplate.getForObject( url, String.class );
        return new ObjectMapper().readValue( jsonString, HashMap.class );
    }

    @GetMapping( "/info" )
    public Map< String, Object > info() throws JsonProcessingException
    {
        Map< String, Object > coindeskMap = coindesk();
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
            currencyItems.put( "name", namesByCodes.get( currencyCode ) );
            currencyItems.put( "rate", currencyJSON.getString( "rate" ) );
            
            info.put( currency, currencyItems );
        }
        return info;
    }
    
    @ExceptionHandler( JsonProcessingException.class )
    public ResponseEntity< ? > handleJsonException( JsonProcessingException e )
    {
        e.printStackTrace();
        log.error( e.getMessage() );

        return ResponseEntity.internalServerError().build();
    }

    private String convertIsoDateFormat( String dateString, String pattern )
    {
        LocalDateTime dateTime = LocalDateTime.parse( dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME );
        return dateTime.format( DateTimeFormatter.ofPattern( pattern ) );
    }
}
