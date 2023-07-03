package demo.currency.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.currency.model.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder( OrderAnnotation.class )
public class CurrencyControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;
    private Currency expected = new Currency( "TWD", "新台幣" );

    @Test
    @Order( 1 )
    void whenCallGetAllMethod_thenVerifyStatus() throws Exception
    {
        MvcResult result = mockMvc.perform( get( "/currencies" ) )
                                  .andExpect( status().isOk() )
                                  .andExpect( jsonPath( "$" ).isArray() )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }

    @Test
    @Order( 2 )
    void whenCallCreateMethod_thenVerifyStatus() throws Exception
    {
        String jsonString = new ObjectMapper().writeValueAsString( expected );
        mockMvc.perform( post( "/currencies" ).contentType( MediaType.APPLICATION_JSON )
                                              .content( jsonString ) )
               .andExpect( status().isCreated() );
    }

    @Test
    @Order( 3 )
    void whenCallGetMethod_thenVerifyStatus() throws Exception
    {
        MvcResult result = mockMvc.perform( get( "/currencies/" + expected.getCode() ) )
                                  .andExpect( status().isOk() )
                                  .andExpect( jsonPath( "$.name" ).value( expected.getName() ) )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }

    @Test
    @Order( 4 )
    void whenCallUpdateMethod_thenVerifyStatus() throws Exception
    {
        String patchString = "台幣";
        MvcResult result = mockMvc.perform( patch( "/currencies/" + expected.getCode() ).contentType( MediaType.APPLICATION_JSON )
                                                                 .content( "{\"name\":\"" + patchString + "\"}" ) )
                                  .andExpect( status().isOk() )
                                  .andExpect( jsonPath( "$.name" ).value( patchString ) )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }

    @Test
    @Order( 5 )
    void whenCallDeleteMethod_thenVerifyStatus() throws Exception
    {
        mockMvc.perform( delete( "/currencies/" + expected.getCode() ) )
               .andExpect( status().isNoContent() );
    }
}