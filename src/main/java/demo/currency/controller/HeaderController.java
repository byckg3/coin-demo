package demo.currency.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import demo.currency.config.AppProperties;
import demo.currency.model.service.HeaderDTO;
import demo.currency.model.service.HeaderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/headers" )
@CrossOrigin( origins = "*" )
public class HeaderController
{
    private AppProperties appProps;
    private HeaderService headerService;
    
    public HeaderController( HeaderService currService, AppProperties props  ) {
        this.headerService = currService;
        this.appProps = props;
    }

    // @GetMapping()  // When the parameter isn't specified, the method parameter is bound to null.
    // public Page< Header > getByDescriptionAndStatus( @RequestParam( required = false ) String description,
    //                                                  @RequestParam( required = false ) String status,
    //                                                  @RequestParam Optional< Integer > page,
    //                                                  @RequestParam Optional< Integer > size )
    @GetMapping()
    public List< HeaderDTO > getByDescriptionAndStatus( @RequestParam Map< String, String > allParams )
    {
        try
        {
            var description = allParams.getOrDefault( "description", null );
            var status = allParams.getOrDefault( "status", null );
            var pageNumber = Integer.valueOf( allParams.getOrDefault( "page", "0" ) );
            var pageSize = Integer.valueOf( allParams.getOrDefault( "size", appProps.getPageSize().toString() ) );
            
            Pageable pageRequest = createPageRequestUsing( pageNumber, pageSize );

            return headerService.getByDescriptionAndStatus( description, status, pageRequest ).getContent();
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Provide correct parameters", ex );
        }
    }

    @GetMapping( "/{id}" ) // @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public HeaderDTO getById( @PathVariable( "id" ) Long id )
    {
        try
        {
            Optional< HeaderDTO > foundHeader = headerService.getById( id );

            return foundHeader.get();
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Header Not Found" );
        }
    }

    @PostMapping( consumes = "application/json" ) // @RequestMapping( consumes = "application/json", method = RequestMethod.POST )
    public ResponseEntity< HeaderDTO > create( @Valid @RequestBody HeaderDTO headerDTO, UriComponentsBuilder ucb )
    {
        var id = headerDTO.getId();
        if ( !ObjectUtils.isEmpty( id ) && headerService.headerExists( id ) )
        {
            throw new ResponseStatusException( HttpStatus.CONFLICT, "Header Already Exists" );
        }

        try
        {
            HeaderDTO createdHeader = headerService.save( headerDTO );

            URI locationUri = ucb.path( "/headers/" )
                                 .path( createdHeader.getId().toString() )
                                 .build()
                                 .toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation( locationUri );

            return new ResponseEntity< HeaderDTO >( createdHeader, httpHeaders, HttpStatus.CREATED );
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurs" );
        }
    }

    @PatchMapping( path = "/{id}", consumes = "application/json" )
    public HeaderDTO update( @PathVariable( "id" ) Long id, @Valid @RequestBody HeaderDTO patch )
    {
        try
        {
            return headerService.editById( id, patch );
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Provide correct Id", ex );
        }
    }

    @DeleteMapping( "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void delete( @PathVariable( "id" ) Long id )
    {
        try {
            headerService.deleteById( id );
        }
        catch( EmptyResultDataAccessException e ) {
            System.out.println("idName" + id );
            log.error( id + " " + e.getMessage() );
        }
    }
    
    private Pageable createPageRequestUsing( int page, int size ) {
        return PageRequest.of( page, size );
    }
}
