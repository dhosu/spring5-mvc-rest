package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.api.v1.model.VendorDTO;
import guru.courses.spring5mvcrest.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String NAME = "VendorName";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendor2VendorDTO() {

        // given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        // when
        VendorDTO vendorDTO = vendorMapper.vendor2VendorDTO(vendor);

        // then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTO2Vendor() {

        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        // when
        Vendor vendor = vendorMapper.vendorDTO2Vendor(vendorDTO);

        // then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }

}
