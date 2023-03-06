package demo.currency.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository< Currency, Long >
{
    Optional< Currency > findByCodeIgnoringCase( String code );
    //List< Coin > findAllByCode( Iterable< String > codeList );
    void deleteByCode( String code );
    boolean existsByCode( String code );
}