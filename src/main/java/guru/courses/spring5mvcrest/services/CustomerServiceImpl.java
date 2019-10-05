package guru.courses.spring5mvcrest.services;

import guru.courses.spring5mvcrest.api.v1.mapper.CustomerMapper;
import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.controllers.v1.CustomerController;
import guru.courses.spring5mvcrest.domain.Customer;
import guru.courses.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customer2CustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customer2CustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTO2Customer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTO2Customer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstname() != null)
                        customer.setFirstname(customerDTO.getFirstname());
                    if (customerDTO.getLastname() != null)
                        customer.setLastname(customerDTO.getLastname());

                    CustomerDTO returnedDTO = customerMapper.customer2CustomerDTO(customer);
                    returnedDTO.setCustomerUrl(getCustomerUrl(id));

                    return returnedDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO saveAndReturnDTO(@NotNull Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDTO = customerMapper.customer2CustomerDTO(savedCustomer);
        returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return returnDTO;
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }

}
