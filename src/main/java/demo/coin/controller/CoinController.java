package demo.coin.controller;

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
import org.springframework.web.client.RestTemplate;

import demo.coin.model.Coin;
import demo.coin.model.CoinService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/coin" )
@CrossOrigin( origins = "*" )
public class CoinController
{
    private CoinService coinService;
    
    @Autowired
    public CoinController( CoinService coinService ) {
        this.coinService = coinService;
    }

    @PostMapping( consumes = "application/json" )
    @ResponseStatus( HttpStatus.CREATED )
    public Coin createCoin( @RequestBody Coin coin )
    {
        return coinService.save( coin );
    }

    @GetMapping( "/{code}" )
    public ResponseEntity< Coin > getCoin( @PathVariable( "code" ) String codeName )
    {
        Optional< Coin > foundCoin = coinService.findByCode( codeName );
        if( foundCoin.isPresent() )
        {
            return ResponseEntity.ok( foundCoin.get() );
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping( path = "/{code}", consumes = "application/json" )
    public Coin updateCoin( @PathVariable( "code" ) String codeName, @RequestBody Coin patch )
    {
        return coinService.updateByCode( codeName, patch );
    }

    @DeleteMapping( "/{code}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteCoin( @PathVariable( "code" ) String codeName )
    {
        try {
            coinService.deleteByCode( codeName );
        }
        catch( EmptyResultDataAccessException e ) {
            System.out.println("codeName" + codeName );
            log.error( codeName + " " + e.getMessage() );
        }
    }
}
