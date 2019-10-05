package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.domain.Category;
import guru.courses.spring5mvcrest.api.v1.model.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO category2CategoryDTO(Category category);
}
