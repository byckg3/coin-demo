package demo.currency.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CurrencyService
{
    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService( CurrencyRepository currRepo ) {
        this.currencyRepository = currRepo;
    }

    public boolean currencyExists( String code ) {
        return currencyRepository.existsByCode( code );
    }

    public Currency save( Currency coin ) {
        return currencyRepository.save( coin );
    }

    public Currency updateByCode( String code, Currency patch )
    {
        Optional< Currency > foundCoin = findByCode( code );
        if ( !foundCoin.isPresent() ) {
            return null;
        }

        Currency updated = foundCoin.get();
        if ( patch.getCode() != null ) {
            updated.setCode( patch.getCode() );
        }
        if ( patch.getName() != null ) {
            updated.setName( patch.getName() );
        }
        return save( updated );
    }

    public void deleteByCode( String code ) {
        currencyRepository.deleteByCode( code );
    }

    public Optional< Currency > findByCode( String code ) {
        return currencyRepository.findByCodeIgnoringCase( code );
    }

    public String getNameByCode( String code )
    {
        Optional< Currency > foundCoin = findByCode( code );
        if ( !foundCoin.isPresent() ) {
            return "";
        }
        return foundCoin.get().getName();
    }

    public Map< String, String > codeToNameMappings()
	{
		Map< String, String > mappings = new HashMap<>();
        for ( Currency each: currencyRepository.findAll() )
        {
            mappings.put( each.getCode(), each.getName() );
        }
        return mappings;
	}
}
