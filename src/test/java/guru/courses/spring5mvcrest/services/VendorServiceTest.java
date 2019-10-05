package guru.courses.spring5mvcrest.services;

import guru.courses.spring5mvcrest.api.v1.mapper.VendorMapper;
import guru.courses.spring5mvcrest.api.v1.model.VendorDTO;
import guru.courses.spring5mvcrest.controllers.v1.VendorController;
import guru.courses.spring5mvcrest.domain.Vendor;
import guru.courses.spring5mvcrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "VendorName";

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Vendor> categories = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(categories);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(3, vendorDTOS.size());
    }

    @Test
    public void getVendorByName() {

        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findByName(anyString())).thenReturn(vendor);

        //when
        VendorDTO vendorDTO = vendorService.getVendorByName(NAME);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }
}