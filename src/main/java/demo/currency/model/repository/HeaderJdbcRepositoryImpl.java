package demo.currency.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import demo.currency.model.Header;


@Repository
public class HeaderJdbcRepositoryImpl implements HeaderJdbcRepository
{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private RowMapper< Header > rowMapper;

    private final static String TABLE_NAME = "headers";
    
    @Autowired
    public HeaderJdbcRepositoryImpl( JdbcOperations jdbcOperations )
    {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate( jdbcOperations );
        this.simpleJdbcInsert = new SimpleJdbcInsert( ( JdbcTemplate ) jdbcOperations ).withTableName( TABLE_NAME )
                                                                                       .usingGeneratedKeyColumns( "id" );
        this.rowMapper = new BeanPropertyRowMapper< Header >( Header.class );
    }

    @Override
    public Header save( Header entity )
    {
        if ( entity.getId() == null )
        {
            Long id = simpleJdbcInsert.executeAndReturnKey( new BeanPropertySqlParameterSource( entity ) )
                                      .longValue();
            entity.setId( id );
        }
		else {
            update( entity );
        }
        return entity;
	}

    @Override
    public List< Header > findAll()
    {
        StringBuffer SELECT_ALL = new StringBuffer();
        SELECT_ALL.append( "SELECT * FROM " ).append( TABLE_NAME ).append( " ORDER BY priority" );

		return namedParameterJdbcTemplate.query( SELECT_ALL.toString(), rowMapper );
    }

    @Override
    public List< Header > findByDescriptionAndStatus( String description, String status )
    {
        StringBuffer SELECT_BY = new StringBuffer();
        SELECT_BY.append( "SELECT * FROM " ).append( TABLE_NAME );
        SELECT_BY.append( " WHERE description LIKE :description AND ");
        SELECT_BY.append( "( :status IS NULL OR status = :status ) ORDER BY priority" );

    
        String descriptionText = StringUtils.hasText( description )? description : "";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue( "description", "%" + descriptionText + "%" )
                                                                  .addValue( "status", status );
                                                                  
		return namedParameterJdbcTemplate.query( SELECT_BY.toString(), params, rowMapper );
    }
    
    @Override
    public Optional< Header > findById( Long id )
    {
        StringBuffer SELECT_BY_ID = new StringBuffer();
        SELECT_BY_ID.append( "SELECT * FROM " ).append( TABLE_NAME );
        SELECT_BY_ID.append( " WHERE id = ?" );

        Header header = null;
        try
        {
            header = namedParameterJdbcTemplate.getJdbcOperations().queryForObject( SELECT_BY_ID.toString(), rowMapper, id );
        }
        catch ( EmptyResultDataAccessException e ) {
            
        }
        return Optional.ofNullable( header );
    }

    @Override
    public boolean existsById( Long id )
    {
        Optional< Header > foundHeader = findById( id );

        return foundHeader.isPresent();
    }
    
    public void update( Header entity )
    {
		StringBuffer UPDATE_BY_ID = new StringBuffer();
		UPDATE_BY_ID.append( "UPDATE " ).append( TABLE_NAME ).append( " SET " );;
        UPDATE_BY_ID.append( "description = :description, url = :url, priority = :priority, status = :status, open_method = :openMethod, " );
        UPDATE_BY_ID.append( "modifier = :modifier, last_modified = :lastModified " );
        UPDATE_BY_ID.append( "WHERE id = :id" );
        
        namedParameterJdbcTemplate.update( UPDATE_BY_ID.toString(), new BeanPropertySqlParameterSource( entity ) );
	}

    @Override
    public void deleteById(  Long id )
    {
        StringBuffer DELETE_BY_ID = new StringBuffer();
        DELETE_BY_ID.append( "DELETE FROM " ).append( TABLE_NAME );
        DELETE_BY_ID.append( " WHERE id = ?" );

         namedParameterJdbcTemplate.getJdbcOperations().update( DELETE_BY_ID.toString(), id );
    }

    public void deleteAllByIdInBatch( Iterable< Long > ids )
    {
        // to do: write sql
        for ( Long id : ids )
        {
            deleteById( id );
        }
    }
}