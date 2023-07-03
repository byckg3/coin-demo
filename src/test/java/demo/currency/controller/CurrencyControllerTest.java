package demo.currency.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import demo.currency.model.Currency;
import demo.currency.model.service.CurrencyService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    private Currency expected;

    @BeforeEach
    void setUp()
    {
        expected = new Currency( "USD", "美元" );
    }

    @Test
    void givenCurrencies_whenMockMVC_thenVerifyResponse() throws Exception
    {
        List< Currency > all = new ArrayList< Currency >();
        all.add( expected );

        Mockito.when( currencyService.getAll() ).thenReturn( all );

        MvcResult result = mockMvc.perform( get( "/currencies" ) )
                                  .andExpect( status().isOk() )
                                  .andExpect( jsonPath( "$[0].code" ).value( expected.getCode() ) )
                                  .andExpect( jsonPath( "$[0].name", is( expected.getName() ) ) )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }
}