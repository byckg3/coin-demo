package demo.currency.model.repository;

import java.util.List;
import java.util.Optional;

import  demo.currency.model.Header;

public interface HeaderJdbcRepository
{
    Header save( Header entity );

    Optional< Header > findById( Long id );
    List< Header > findAll();
    List< Header > findByDescriptionAndStatus( String description, String status );

    boolean existsById(  Long id );

    void deleteById(  Long id  );
    //void deleteAllByIdInBatch( Iterable< Long > ids );
}
