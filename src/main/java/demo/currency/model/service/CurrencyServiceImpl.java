package demo.currency.model.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import demo.currency.model.exception.CurrencyNotFoundException;
import demo.currency.model.repository.Currency;
import demo.currency.model.repository.CurrencyRepository;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService
{
    private CurrencyRepository currencyRepository;

    public CurrencyServiceImpl( CurrencyRepository currRepo ) {
        this.currencyRepository = currRepo;
    }

    @Override
    public boolean currencyExists( String code ) {
        return currencyRepository.existsByCode( code );
    }

    @Override
    public List< Currency > getAll() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency save( Currency curr ) {
        return currencyRepository.save( curr );
    }

    @Override
    public void deleteByCode( String code ) {
        currencyRepository.deleteByCode( code );
    }

    @Override
    public Optional< Currency > findByCode( String code ) {
        return currencyRepository.findByCodeIgnoringCase( code );
    }

    @Override
    public Currency updateByCode( String code, Currency patch ) throws CurrencyNotFoundException
    {
        Optional< Currency > foundCurrency = findByCode( code );
        if ( !foundCurrency.isPresent() ) {
            throw new CurrencyNotFoundException( code );
        }

        Currency updated = foundCurrency.get();
        if ( patch.getCode() != null ) {
            updated.setCode( patch.getCode() );
        }
        if ( patch.getName() != null ) {
            updated.setName( patch.getName() );
        }
        return save( updated );
    }
}
