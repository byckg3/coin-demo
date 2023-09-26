package demo.currency.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import demo.currency.model.Header;

// In order to allow individual test methods to be executed in isolation and to avoid unexpected side effects due to mutable test instance state, 
// JUnit creates a new instance of each test class before executing each test method.
@SpringBootTest
@TestMethodOrder( OrderAnnotation.class )
public class HeaderJdbcRepositoryTest
{
    @Autowired
    private HeaderJdbcRepository headerRepo;

    private static Header header;
    private static Long createdId;
    private static String expectedStatus = "1";
    private static String expectedDescription = "testing Description";
    private static String expectedUrl = "www.test.com";

    @BeforeAll
    static void setUp()
    {
        header = new Header();
        header.setDescription( expectedDescription );
        header.setPriority( 1 );
        header.setStatus( expectedStatus );
        header.setOpenMethod( "1" );
        header.setCreator( "admin" );
        header.setModifier( "admin" );

        OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC );
        header.setCreatedDate( now );
        header.setLastModified( now );
    }

    @AfterAll
    static void tearDown()
    {
       
    }

    @Test
    @Order( 1 )
    void whenSaveEntity_thenSaveSuccessfully( )
    {
        assumeRepositoryExists();

        Header savedHeader = headerRepo.save( header );
        createdId = savedHeader.getId();

        assertNotNull( createdId );
    }

    @Test
    @Order( 2 )
    void whenFindEntity_thenEntityExists( )
    {
        Optional< Header > foundHeader = headerRepo.findById( createdId );
        
        assertTrue( foundHeader.isPresent() );
    }

    @Test
    @Order( 3 )
    void whenUpdateEntity_thenUpdateSuccessfully( )
    {
        
        header.setUrl( expectedUrl );
        headerRepo.save( header );

        Optional< Header > foundHeader = headerRepo.findById( createdId );

        assertTrue( foundHeader.isPresent() );
        assertEquals( expectedUrl, foundHeader.get().getUrl() );
    }

    @Test
    @Order( 4 )
    void whenFindHeaderByDescriptionAndStatus_thenEntitiesExist( )
    {
        List< Header > foundList = headerRepo.findByDescriptionAndStatus( expectedDescription, expectedStatus );
        
        assertTrue( !foundList.isEmpty() );
        assertEquals( expectedDescription, foundList.get( 0 ).getDescription() );
        assertEquals( expectedStatus, foundList.get( 0 ).getStatus() );
    }

    @Test
    @Order( 5 )
    void whenFindHeaderByDescriptionAndNullStatus_thenIgnoreNull( )
    {
        List< Header > ignoredNullStatusList = headerRepo.findByDescriptionAndStatus( expectedDescription, null );
        
        assertTrue( !ignoredNullStatusList.isEmpty() );
        assertEquals( expectedDescription, ignoredNullStatusList.get( 0 ).getDescription() );
    }

    @Test
    @Order( 6 )
    void whenFindHeaderByNullDescriptionAndStatus_thenIgnoreNull( )
    {
        List< Header > ignoredNullDescriptionList = headerRepo.findByDescriptionAndStatus( null, expectedStatus );
        
        assertTrue( !ignoredNullDescriptionList.isEmpty() );
        assertEquals( expectedStatus, ignoredNullDescriptionList.get( 0 ).getStatus() );
    }

    @Test
    @Order( 7 )
    void whenDeleteEntityById_thenEntityWasNotFound( )
    {
        headerRepo.deleteById( header.getId() );
        Optional< Header > foundHeader = headerRepo.findById( createdId );
        
        assertFalse( foundHeader.isPresent() );
    }

    private void assumeRepositoryExists()
    {
        assumeTrue( headerRepo != null, "repository is not injected" );
    }
}