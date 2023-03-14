package demo.currency.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository< Currency, Long >
{
    Optional< Currency > findByCode( String code );
    Optional< Currency > findByCodeIgnoringCase( String code );
    void deleteByCode( String code );
    boolean existsByCode( String code );
}