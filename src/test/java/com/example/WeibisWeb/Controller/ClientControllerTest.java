package com.example.WeibisWeb.Controller;

import com.example.WeibisWeb.controller.ClientController;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.security.JwtAuthEntrypoint;
import com.example.WeibisWeb.security.JwtFilter;
import com.example.WeibisWeb.security.JwtUtil;
import com.example.WeibisWeb.service.ClientServiceImpl;
import com.example.WeibisWeb.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit testing for the ClientController layer
 */
@WebMvcTest(controllers = ClientController.class)
@ActiveProfiles("test")
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ClientServiceImpl clientService;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private ClientController clientController;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private JwtAuthEntrypoint jwtAuthEntrypoint;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.clientController = new ClientController(this.clientService);
    }

    @Test
    void getAllClients_ShouldReturnListOfObjects_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        ClientDTO clientDTO1 = ClientDTO.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        final UUID id2 = UUID.randomUUID();
        ClientDTO clientDTO2 = ClientDTO.builder().clientId(id2).companyName("Alten").sector("IT")
                .city("Paris").country("France").size(2000).build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        List<ClientDTO> clientDTOList = new ArrayList<>(Arrays.asList(clientDTO1, clientDTO2));

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);
        when(clientService.getAllClient()).thenReturn(clientDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenString)
                        .content(om.writeValueAsString(clientDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(clientDTOList.size())))
                .andExpect(jsonPath("$[0].clientId", is(clientDTOList.get(0).getClientId().toString())))
                .andExpect(jsonPath("$[0].companyName", is(clientDTOList.get(0).getCompanyName())))
                .andExpect(jsonPath("$[0].sector", is(clientDTOList.get(0).getSector())))
                .andExpect(jsonPath("$[1].clientId", is(clientDTOList.get(1).getClientId().toString())))
                .andExpect(jsonPath("$[1].companyName", is(clientDTOList.get(1).getCompanyName())))
                .andExpect(jsonPath("$[1].sector", is(clientDTOList.get(1).getSector())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<ClientDTO> myObjectsRes = om.readValue(jsonResponse, om.getTypeFactory().constructCollectionType(List.class, ClientDTO.class));

        assertNotNull(myObjectsRes);
        assertEquals("Atos", myObjectsRes.get(0).getCompanyName());
        assertEquals("Paris", myObjectsRes.get(0).getCity());
        assertEquals("Alten", myObjectsRes.get(1).getCompanyName());
        assertEquals("Paris", myObjectsRes.get(1).getCity());
    }

    @Test
    void getClientById_ShouldReturnListOfObjects_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        ClientDTO clientDTO1 = ClientDTO.builder().clientId(id1).companyName("Atos").sector("IT")
                .city("Paris").country("France").size(1000).build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);
        when(clientService.getClientById(id1)).thenReturn(clientDTO1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/client?id="+id1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(clientDTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clientId", is(clientDTO1.getClientId().toString())))
                .andExpect(jsonPath("$.companyName", is(clientDTO1.getCompanyName())))
                .andExpect(jsonPath("$.sector", is(clientDTO1.getSector())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        ClientDTO clientDTO = new ObjectMapper().readValue(jsonResponse, ClientDTO.class);

        assertNotNull(clientDTO);
        assertEquals("Atos", clientDTO.getCompanyName());
        assertEquals("IT", clientDTO.getSector());
    }
}
