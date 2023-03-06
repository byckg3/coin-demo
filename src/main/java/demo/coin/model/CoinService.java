package demo.coin.model;

import java.util.HashMap;
import java.util.Map;
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
        return save( updated );
    }

    public void deleteByCode( String code ) {
        coinRepository.deleteByCode( code );
    }

    public Optional< Coin > findByCode( String code ) {
        return coinRepository.findByCodeIgnoringCase( code );
    }

    public String getNameByCode( String code )
    {
        Optional< Coin > foundCoin = findByCode( code );
        if ( !foundCoin.isPresent() ) {
            return "";
        }
        return foundCoin.get().getName();
    }

    public Map< String, String > codeToNameMappings()
	{
		Map< String, String > mappings = new HashMap<>();
        for ( Coin each: coinRepository.findAll() )
        {
            mappings.put( each.getCode(), each.getName() );
        }
        return mappings;
	}
}
