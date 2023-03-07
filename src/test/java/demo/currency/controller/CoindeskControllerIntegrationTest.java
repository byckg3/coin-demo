package demo.currency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoindeskControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCallCoindeskMethod_thenVerifyStatus() throws Exception
    {
        MvcResult result = mockMvc.perform( get( "/coindesk" ) )
                                  .andExpect( status().isOk() )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }

    @Test
    void whenCallInfoMethod_thenVerifyStatus() throws Exception
    {
        MvcResult result = mockMvc.perform( get( "/coindesk/info" ) )
                                  .andExpect( status().isOk() )
                                  .andReturn();
        
        System.out.println( result.getResponse().getContentAsString() );
    }
}
