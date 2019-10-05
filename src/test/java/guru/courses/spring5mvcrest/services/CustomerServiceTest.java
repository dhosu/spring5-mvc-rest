package guru.courses.spring5mvcrest.services;

import guru.courses.spring5mvcrest.api.v1.mapper.CustomerMapper;
import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.domain.Customer;
import guru.courses.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    public static final Long ID = 2L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Dow";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {

        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {

        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        // then
        assertEquals(FIRST_NAME, customerDTO.getFirstname());
        assertEquals(LAST_NAME, customerDTO.getLastname());
        assertEquals("/api/v1/customers/" + ID, customerDTO.getCustomerUrl());
    }

    @Test
    public void createNewCustomer() {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() {

        Long id = 1L;

        customerRepository.deleteById(id);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}