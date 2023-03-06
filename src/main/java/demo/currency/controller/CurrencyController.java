package demo.currency.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.currency.model.Currency;
import demo.currency.model.CurrencyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/coin" )
@CrossOrigin( origins = "*" )
public class CurrencyController
{
    private CurrencyService currencyService;
    
    @Autowired
    public CurrencyController( CurrencyService currService ) {
        this.currencyService = currService;
    }

    @PostMapping( consumes = "application/json" )
    @ResponseStatus( HttpStatus.CREATED )
    public Currency createCoin( @RequestBody Currency coin )
    {
        return currencyService.save( coin );
    }

    @GetMapping( "/{code}" )
    public ResponseEntity< Currency > getCoin( @PathVariable( "code" ) String code )
    {
        Optional< Currency > foundCoin = currencyService.findByCode( code );
        if( foundCoin.isPresent() )
        {
            return ResponseEntity.ok( foundCoin.get() );
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping( path = "/{code}", consumes = "application/json" )
    public Currency updateCoin( @PathVariable( "code" ) String code, @RequestBody Currency patch )
    {
        return currencyService.updateByCode( code, patch );
    }

    @DeleteMapping( "/{code}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteCoin( @PathVariable( "code" ) String codeName )
    {
        try {
            currencyService.deleteByCode( codeName );
        }
        catch( EmptyResultDataAccessException e ) {
            System.out.println("codeName" + codeName );
            log.error( codeName + " " + e.getMessage() );
        }
    }
}
