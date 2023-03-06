package demo.currency.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

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
