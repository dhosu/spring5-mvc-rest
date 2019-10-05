package guru.courses.spring5mvcrest.api.v1.mapper;

import guru.courses.spring5mvcrest.api.v1.model.VendorDTO;
import guru.courses.spring5mvcrest.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendor2VendorDTO(Vendor vendor);

    Vendor vendorDTO2Vendor(VendorDTO vendorDTO);
}
