package com.example.WeibisWeb.Controller;

import com.example.WeibisWeb.controller.CandidateController;
import com.example.WeibisWeb.controller.ClientController;
import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.resources.Client;
import com.example.WeibisWeb.security.JwtAuthEntrypoint;
import com.example.WeibisWeb.security.JwtFilter;
import com.example.WeibisWeb.security.JwtUtil;
import com.example.WeibisWeb.service.ClientServiceImpl;
import com.example.WeibisWeb.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * JUnit testing for the ClientController layer
 */
@WebMvcTest(controllers = ClientController.class)
@ActiveProfiles("test")
public class ClientControllerTest {

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
        MockitoAnnotations.initMocks(this);
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
//                .andExpect(jsonPath("$[0].name", is(clientDTOList.get(0).getName())))
//                .andExpect(jsonPath("$[0].lastName", is(clientDTOList.get(0).getLastName())))
//                .andExpect(jsonPath("$[0].dob", is(clientDTOList.get(0).getDob())))
                .andExpect(jsonPath("$[1].clientId", is(clientDTOList.get(1).getClientId().toString())))
//                .andExpect(jsonPath("$[1].name", is(clientDTOList.get(1).getName())))
//                .andExpect(jsonPath("$[1].lastName", is(clientDTOList.get(1).getLastName())))
//                .andExpect(jsonPath("$[1].dob", is(clientDTOList.get(1).getDob())))
                .andReturn();
    }
}
