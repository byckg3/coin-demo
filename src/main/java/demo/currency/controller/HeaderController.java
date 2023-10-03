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

import demo.currency.model.Header;
import demo.currency.model.service.HeaderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( path = "/headers" )
@CrossOrigin( origins = "*" )
public class HeaderController
{
    private HeaderService headerService;
    
    public HeaderController( HeaderService currService ) {
        this.headerService = currService;
    }

    // @GetMapping()  // When the parameter isn't specified, the method parameter is bound to null.
    // public Page< Header > getByDescriptionAndStatus( @RequestParam( required = false ) String description,
    //                                                  @RequestParam( required = false ) String status,
    //                                                  @RequestParam Optional< Integer > page,
    //                                                  @RequestParam Optional< Integer > size )
    @GetMapping()
    public List< Header > getByDescriptionAndStatus( @RequestParam Map< String, String > allParams )
    {
        try
        {
            String description = allParams.getOrDefault( "description", null );
            String status = allParams.getOrDefault( "status", null );
            Integer pageNumber = Integer.valueOf( allParams.getOrDefault( "page", "0" ) );
            Integer pageSize = Integer.valueOf( allParams.getOrDefault( "size", "20" ) );

            Pageable pageRequest = createPageRequestUsing( pageNumber, pageSize );

            return headerService.getByDescriptionAndStatus( description, status, pageRequest ).getContent();
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Provide correct parameters", ex );
        }
    }

    @GetMapping( "/{id}" ) // @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @ResponseStatus( HttpStatus.OK )
    public Header getById( @PathVariable( "id" ) Long id )
    {
        try
        {
            Optional< Header > foundHeader = headerService.getById( id );

            return foundHeader.get();
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Header Not Found" );
        }
    }

    @PostMapping( consumes = "application/json" ) // @RequestMapping( consumes = "application/json", method = RequestMethod.POST )
    public ResponseEntity< Header > create( @RequestBody Header header, UriComponentsBuilder ucb )
    {
        var id = header.getId();
        if ( !ObjectUtils.isEmpty( id ) && headerService.headerExists( id ) )
        {
            throw new ResponseStatusException( HttpStatus.CONFLICT, "Header Already Exists" );
        }

        try
        {
            Header createdHeader = headerService.save( header );

            URI locationUri = ucb.path( "/headers/" )
                                 .path( createdHeader.getId().toString() )
                                 .build()
                                 .toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation( locationUri );

            return new ResponseEntity< Header >( createdHeader, httpHeaders, HttpStatus.CREATED );
        }
        catch( Exception ex )
        {
            throw new ResponseStatusException( HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurs" );
        }
    }

    @PatchMapping( path = "/{id}", consumes = "application/json" )
    public Header update( @PathVariable( "id" ) Long id, @RequestBody Header patch )
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
