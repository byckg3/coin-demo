package demo.currency.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.currency.model.CoindeskClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/coindesk" )
@CrossOrigin( origins = "*" )
public class CoindeskController
{
    private CoindeskClient coindeskClient;

    @Autowired
    public CoindeskController( CoindeskClient client )
    {
        this.coindeskClient = client;
    }

    @GetMapping()
    public Map< String, Object > coindesk() throws JsonProcessingException
    {
        return coindeskClient.getCurrentPrice();
    }

    @GetMapping( "/info" )
    public Map< String, Object > info() throws JsonProcessingException
    {
        return coindeskClient.getCurrentInfo();
    }
    
    @ExceptionHandler( JsonProcessingException.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public String handleJsonException( JsonProcessingException e )
    {
        e.printStackTrace();
        log.error( e.getMessage() );

        return e.getMessage();
    }
}
