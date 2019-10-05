package guru.courses.spring5mvcrest.repositories;


import guru.courses.spring5mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}