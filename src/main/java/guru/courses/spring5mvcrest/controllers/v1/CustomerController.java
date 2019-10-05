package guru.courses.spring5mvcrest.controllers.v1;

import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.api.v1.model.CustomerListDTO;
import guru.courses.spring5mvcrest.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CustomerDTO getCustomerById(@PathVariable Long id) {

        CustomerDTO customer = customerService.getCustomerById(id);
        customer.setCustomerUrl(BASE_URL + "/" + id);

        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
