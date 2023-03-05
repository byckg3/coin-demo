package demo.coin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CoinService
{
    private CoinRepository coinRepository;

    @Autowired
    public CoinService( CoinRepository coinRepo ) {
        this.coinRepository = coinRepo;
    }

    public boolean coinExists( String code ) {
        return coinRepository.existsByCode( code );
    }

    public Coin save( Coin coin ) {
        return coinRepository.save( coin );
    }

    public Coin updateByCode( String code, Coin patch )
    {
        Optional< Coin > foundCoin = findByCode( code );
        if ( !foundCoin.isPresent() ) {
            return null;
        }

        Coin updated = foundCoin.get();
        if ( patch.getCode() != null ) {
            updated.setCode( patch.getCode() );
        }
        if ( patch.getName() != null ) {
            updated.setName( patch.getName() );
        }
        
        return coinRepository.save( updated );
    }

    public void deleteByCode( String code ) {
        coinRepository.deleteByCode( code );
    }

    public Optional< Coin > findByCode( String code ) {
        return coinRepository.findByCodeIgnoringCase( code );
    }

    public List< Coin > info()
    {
        List< Coin > info = new ArrayList<>();


        return info;
    }
}
