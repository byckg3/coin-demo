package demo.currency.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.currency.config.AppProperties;
import demo.currency.model.service.HeaderDTO;
import demo.currency.model.service.HeaderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//@SpringBootTest
@WebMvcTest( HeaderController.class )
@AutoConfigureMockMvc
public class HeaderControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeaderService headerService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public AppProperties appProperties()
        {
            var appProperties = new AppProperties();
            appProperties.setPageSize( 10 );

            return appProperties;
        }
    }

    private String validUrl = "http://www.abc.com";
    private String invalidUrl = "www.abc.com";
    private HeaderDTO dto;

    @BeforeEach
    void setUp()
    {
        dto = new HeaderDTO();
        dto.setDescription( "testing description" );
        dto.setUrl( validUrl );
        dto.setPriority( 1 );
        dto.setOpenMethod( "1" );
        dto.setStatus( "1" );
        dto.setCreator( "admin" );
    }

    @Test
    void whenPostRequestToCreateHeader_thenCorrectResponse() throws Exception
    {
        String jsonString = new ObjectMapper().writeValueAsString( dto );

        Long expectedId = 1L;
        dto.setId( expectedId );
        given( headerService.save( any( HeaderDTO.class ) ) ).willReturn( dto );

        MvcResult result = mockMvc.perform( post( "/headers" ).contentType( MediaType.APPLICATION_JSON ).content( jsonString ) )
                                  .andExpect( status().isCreated() )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }

    @Test
    void whenPostRequestToCreateHeaderWithInvalidUrl_thenValidationFailed() throws Exception
    {
        dto.setUrl( invalidUrl );
        String jsonString = new ObjectMapper().writeValueAsString( dto );

        Long expectedId = 1L;
        dto.setId( expectedId );
        given( headerService.save( any( HeaderDTO.class ) ) ).willReturn( dto );

        MvcResult result = mockMvc.perform( post( "/headers" ).contentType( MediaType.APPLICATION_JSON ).content( jsonString ) )
                                  .andExpect( status().isBadRequest() )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }
}
