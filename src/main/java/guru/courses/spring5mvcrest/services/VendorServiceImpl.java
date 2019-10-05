package guru.courses.spring5mvcrest.services;

import guru.courses.spring5mvcrest.api.v1.mapper.VendorMapper;
import guru.courses.spring5mvcrest.api.v1.model.VendorDTO;
import guru.courses.spring5mvcrest.controllers.v1.VendorController;
import guru.courses.spring5mvcrest.domain.Vendor;
import guru.courses.spring5mvcrest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendor2VendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorByName(String name) {
        return vendorMapper.vendor2VendorDTO(vendorRepository.findByName(name));
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendor2VendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDTO2Vendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDTO2Vendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null)
                        vendor.setName(vendorDTO.getName());

                    VendorDTO returnedDTO = vendorMapper.vendor2VendorDTO(vendor);
                    returnedDTO.setVendorUrl(getVendorUrl(id));

                    return returnedDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDTO(@NotNull Vendor vendor) {

        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDTO = vendorMapper.vendor2VendorDTO(savedVendor);
        returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnDTO;
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

}
