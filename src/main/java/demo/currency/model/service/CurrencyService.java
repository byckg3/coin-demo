package demo.currency.model.service;

import java.util.List;
import java.util.Optional;

import demo.currency.model.Currency;

public interface CurrencyService
{
    boolean currencyExists( String code );
    List< Currency > getAll();
    Currency save( Currency curr );
    Currency updateByCode( String code, Currency patch );
    Optional< Currency > findByCode( String code );
    void deleteByCode( String code );
}