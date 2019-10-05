package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Dow";
    public static final long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {

        // given
        Customer customer = new Customer();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);
        customer.setId(ID);

        // when
        CustomerDTO customerDTO = customerMapper.customer2CustomerDTO(customer);

        // then
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    public void customerDTO2Customer() {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        // when
        Customer customer = customerMapper.customerDTO2Customer(customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }

}