package guru.courses.spring5mvcrest.repositories;

import guru.courses.spring5mvcrest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
