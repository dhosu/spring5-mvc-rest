package guru.courses.spring5mvcrest.bootstrap;

import guru.courses.spring5mvcrest.domain.Category;
import guru.courses.spring5mvcrest.domain.Customer;
import guru.courses.spring5mvcrest.domain.Vendor;
import guru.courses.spring5mvcrest.repositories.CategoryRepository;
import guru.courses.spring5mvcrest.repositories.CustomerRepository;
import guru.courses.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {

        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {

        Customer joe = new Customer();
        joe.setFirstname("Joe");
        joe.setLastname("Newman");

        Customer michael = new Customer();
        michael.setFirstname("Michael");
        michael.setLastname("Lachappele");

        Customer david = new Customer();
        david.setFirstname("David");
        david.setLastname("Winter");

        Customer anne = new Customer();
        anne.setFirstname("Anne");
        anne.setLastname("Hine");

        Customer alice = new Customer();
        alice.setFirstname("Alice");
        alice.setLastname("Eastman");

        Customer joel = new Customer();
        joel.setFirstname("Joel");
        joel.setLastname("Godman");

        customerRepository.save(joe);
        customerRepository.save(michael);
        customerRepository.save(david);
        customerRepository.save(anne);
        customerRepository.save(alice);
        customerRepository.save(joel);

        System.out.println("Customers loaded = " + customerRepository.count());
    }

    private void loadVendors() {

        Vendor vendor1 = new Vendor();
        vendor1.setName("Western Tasty Fruits Ltd.");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Exotic Fruits Company");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Home Fruits");

        Vendor vendor4 = new Vendor();
        vendor4.setName("Fun Fresh Fruits Ltd");

        Vendor vendor5 = new Vendor();
        vendor5.setName("Nuts for Nuts Company");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
        vendorRepository.save(vendor5);
    }
}
