package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.api.v1.model.CustomerDTO;
import guru.courses.spring5mvcrest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customer2CustomerDTO(Customer customer);

    Customer customerDTO2Customer(CustomerDTO customerDTO);
}
