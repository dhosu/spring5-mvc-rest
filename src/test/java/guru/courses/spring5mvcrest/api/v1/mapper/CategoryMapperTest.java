package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.domain.Category;
import guru.courses.spring5mvcrest.api.v1.model.CategoryDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {

        // given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        // when
        CategoryDTO categoryDTO = categoryMapper.category2CategoryDTO(category);

        // then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }

}
