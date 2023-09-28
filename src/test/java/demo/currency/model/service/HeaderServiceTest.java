package demo.currency.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import demo.currency.model.Header;
import demo.currency.model.repository.HeaderJdbcRepository;

@ExtendWith( MockitoExtension.class )
public class HeaderServiceTest
{
    @Mock
    private HeaderJdbcRepository headerRepository;

    @InjectMocks
    private HeaderService headerService;

    private static final List< Header > ALL_HEADERS = new ArrayList<>();
    private static final int expectedTotalElements = 20;

    @BeforeAll
    static void init()
    {
        for ( int i = 0; i < expectedTotalElements; i ++ )
        {
            var header = new Header();
            header.setDescription( "description: " + i );
            header.setStatus( "1" );
            ALL_HEADERS.add( header );
        }
    }

    @BeforeEach
    void setup() {
        when( headerRepository.findByDescriptionAndStatus( anyString(), anyString() ) ).thenReturn( ALL_HEADERS );
    }

    @ParameterizedTest
    @MethodSource( "testInputData" )
    void givenAListOfHeaders_whenGetHeaders_thenReturnsDesiredDataAlongWithPagingInformation( int pageNumber, int pageSize )
    {
        var usingPageRequest = PageRequest.of( pageNumber, pageSize );
        int indexOfFirst = ( int ) usingPageRequest.getOffset();
        
        int expectedTotalPages = ( int ) Math.ceil( expectedTotalElements / ( double ) pageSize ); // index of page: 0 ~ ( TotalPages - 1 )
        int expectedElements = Math.min( expectedTotalElements - indexOfFirst, pageSize );

        Page< Header > headersPage = headerService.getByDescriptionAndStatus( "description", "1", usingPageRequest );
        var headers = headersPage.getContent();
        
        assertEquals( expectedElements, headers.size() );
        assertThat( headers.get( 0 ).getDescription(), containsString( indexOfFirst + "" ) );
        
        assertEquals( expectedTotalElements, headersPage.getTotalElements());
        assertEquals( expectedTotalPages, headersPage.getTotalPages() );
    }

    private static Stream< Arguments > testInputData()
    {
        return Stream.of( Arguments.of( 0, 6 ), 
                          Arguments.of( 1, 6 ),
                          Arguments.of( 2, 6 ),
                          Arguments.of( 3, 6 ) );
    }
}
