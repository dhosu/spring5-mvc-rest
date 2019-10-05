package guru.courses.spring5mvcrest.controllers.v1;

import guru.courses.spring5mvcrest.api.v1.model.VendorDTO;
import guru.courses.spring5mvcrest.controllers.RestExceptionHandler;
import guru.courses.spring5mvcrest.services.ResourceNotFoundException;
import guru.courses.spring5mvcrest.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    private static final String NAME_1 = "VendorName 1";
    private static final String NAME_2 = "VendorName 2";

    private static final String URL = VendorController.BASE_URL + "/1";
    public static final String NEW_VENDOR_NAME = "NewVendorName";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void listVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME_1);

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName(NAME_2);

        List<VendorDTO> vendors = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getByIdVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME_1);
        vendor1.setVendorUrl(URL);

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void createNewVendor() throws Exception {

        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NEW_VENDOR_NAME);

        VendorDTO returnedDTO = new VendorDTO();
        returnedDTO.setName(vendorDTO.getName());
        returnedDTO.setVendorUrl(URL);

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnedDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NEW_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void updateVendor() throws Exception {


        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NEW_VENDOR_NAME);

        VendorDTO returnedDTO = new VendorDTO();
        returnedDTO.setName(vendorDTO.getName());
        returnedDTO.setVendorUrl(URL);

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDTO);

        // when/then
        mockMvc.perform(put(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NEW_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void patchVendor() throws Exception {

        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NEW_VENDOR_NAME);

        VendorDTO returnedDTO = new VendorDTO();
        returnedDTO.setName(vendorDTO.getName());
        returnedDTO.setVendorUrl(URL);

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDTO);

        // when/then

        mockMvc.perform(put(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NEW_VENDOR_NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void deleteVendor() throws Exception {

        mockMvc.perform(delete(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void notFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
