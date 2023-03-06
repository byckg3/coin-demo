package demo.currency.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository< Currency, Long >
{
    Optional< Currency > findByCodeIgnoringCase( String code );
    List< Currency > findAll();
    void deleteByCode( String code );
    boolean existsByCode( String code );
}