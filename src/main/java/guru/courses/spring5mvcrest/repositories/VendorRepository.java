package guru.courses.spring5mvcrest.repositories;


import guru.courses.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findByName(String name);
}
