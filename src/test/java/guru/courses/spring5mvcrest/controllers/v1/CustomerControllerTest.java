package guru.courses.spring5mvcrest.controllers.v1;

import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.controllers.RestExceptionHandler;
import guru.courses.spring5mvcrest.services.CustomerService;
import guru.courses.spring5mvcrest.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest  {

    private static final String FIRST_NAME_1 = "John";
    private static final String LAST_NAME_1 = "Dow";

    private static final String FIRST_NAME_2 = "Joe";
    private static final String LAST_NAME_2 = "Smith";

    private static final String URL = CustomerController.BASE_URL + "/1";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void listCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRST_NAME_1);
        customer1.setLastname(LAST_NAME_1);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname(FIRST_NAME_2);
        customer2.setLastname(LAST_NAME_2);

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getByIdCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRST_NAME_1);
        customer1.setLastname(LAST_NAME_1);
        customer1.setCustomerUrl(URL);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

    @Test
    public void createNewCustomer() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname(customerDTO.getFirstname());
        returnedDTO.setLastname(customerDTO.getLastname());
        returnedDTO.setCustomerUrl(URL);

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnedDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

    @Test
    public void updateCustomer() throws Exception {

        
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname(customerDTO.getFirstname());
        returnedDTO.setLastname(customerDTO.getLastname());
        returnedDTO.setCustomerUrl(URL);

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);

        // when/then
        mockMvc.perform(put(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

    @Test
    public void patchCustomer() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname(customerDTO.getFirstname());
        returnedDTO.setLastname("Flintstone");
        returnedDTO.setCustomerUrl(URL);

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);

        // when/then
        
        mockMvc.perform(put(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

    @Test
    public void deleteCustomer() throws Exception {

        mockMvc.perform(delete(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void notFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}