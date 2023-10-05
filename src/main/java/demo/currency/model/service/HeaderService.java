package demo.currency.model.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import demo.currency.model.Header;
import demo.currency.model.repository.HeaderJdbcRepository;

@Service
@Transactional
public class HeaderService
{
    private HeaderJdbcRepository headerJdbcRepository;

    @Autowired
    public HeaderService( HeaderJdbcRepository headerRepo ) {
        this.headerJdbcRepository = headerRepo;
    }

    public boolean headerExists( Long id ) {
        return headerJdbcRepository.existsById( id );
    }

    public Optional< HeaderDTO > getById( Long id )
    {
        var optHeader = headerJdbcRepository.findById( id );

        return optHeader.map( HeaderMapper::toHeaderDTO );
    }

    public List< HeaderDTO > getAll()
    {
        var headers = headerJdbcRepository.findAll();

        return HeaderMapper.toHeaderDTOs( headers );
    }

    public Page< HeaderDTO > getByDescriptionAndStatus( String description, String status, Pageable pageRequest )
    {
        List< Header > foundHeaders = headerJdbcRepository.findByDescriptionAndStatus( description, status );

        int start = Math.min( ( int ) pageRequest.getOffset(), foundHeaders.size() );
        int end = Math.min( ( start + pageRequest.getPageSize() ), foundHeaders.size() );

        List< HeaderDTO > pageContent = HeaderMapper.toHeaderDTOs( foundHeaders.subList( start, end ) );

        return new PageImpl< HeaderDTO >( pageContent, pageRequest, foundHeaders.size() );
    }

    public HeaderDTO save( HeaderDTO headerDTO )
    {
        var header = HeaderMapper.toHeader( headerDTO );
        var savedHeader = save( header );

        return HeaderMapper.toHeaderDTO( savedHeader );
    }

    public void deleteById( Long id ) {
        headerJdbcRepository.deleteById( id );
    }

    public void deleteAllById( Iterable< Long > ids )
    {
        for ( Long id : ids ) {
            deleteById( id );
        }
    }

    public HeaderDTO editById( Long id, HeaderDTO patch )
    {
        Optional< Header > foundHeader = headerJdbcRepository.findById( id );
        if ( !foundHeader.isPresent() ) {
            // to do
            throw new RuntimeException( "Header not found" );
        }
        
        // to refactor
        Header updated = foundHeader.get();
        if ( StringUtils.hasText( patch.getDescription() ) ) {
            updated.setDescription( patch.getDescription() );
        }
        if ( StringUtils.hasText( patch.getUrl() ) ) {
            updated.setUrl( patch.getUrl() );
        }
        if ( !ObjectUtils.isEmpty( patch.getPriority() ) ) {
            updated.setPriority( patch.getPriority() );
        }
        if ( StringUtils.hasText( patch.getStatus() ) ) {
            updated.setStatus( patch.getStatus() );
        }
        if ( StringUtils.hasText( patch.getModifier() ) ) {
            updated.setModifier( patch.getModifier() );
        }
        var updatedHeader = save( updated );

        return HeaderMapper.toHeaderDTO( updatedHeader );
    }

    public void updateAllById( Iterable< Long > ids, HeaderDTO patch )
    {
        for ( Long id : ids ) {
            editById( id, patch );
        }
    }

    private Header save( Header header )
    {
        var lastModified = OffsetDateTime.now( ZoneOffset.UTC );

        header.setLastModified( lastModified );
        if ( ObjectUtils.isEmpty( header.getId() ) ) {
			header.setCreatedDate( lastModified );
		}

        return headerJdbcRepository.save( header );
    }
}
