package com.example.WeibisWeb.Controller;//package controllerTest;

import com.example.WeibisWeb.controller.CandidateController;
import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.security.JwtAuthEntrypoint;
import com.example.WeibisWeb.security.JwtFilter;
import com.example.WeibisWeb.security.JwtUtil;
import com.example.WeibisWeb.service.CandidateServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit testing for the CandidateController layer
 */
@WebMvcTest(controllers = CandidateController.class)
@ActiveProfiles("test")
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CandidateServiceImpl candidateServiceImpl;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private CandidateController candidateController;

    @MockBean
    private CandidateRepository candidateRepository;

    @MockBean
    private JwtAuthEntrypoint jwtAuthEntrypoint;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JwtUtil jwtUtil;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.candidateController = new CandidateController(this.candidateServiceImpl);
    }

    @Test
    void getCandidatesByCandidateId_ShouldReturnAnObject_ValidReturn() throws Exception {
        final UUID id = UUID.randomUUID();
        CandidateDTO candidateDTO = CandidateDTO.builder()
                .candidateId(id).name("Renos").lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(candidateServiceImpl.getCandidatesById(id)).thenReturn(candidateDTO);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/candidate?id=" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(candidateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.candidateId", is(candidateDTO.getCandidateId().toString())))
                .andExpect(jsonPath("$.name", is(candidateDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(candidateDTO.getLastName())))
                .andExpect(jsonPath("$.dob", is(candidateDTO.getDob())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        CandidateDTO candidateDTORes = new ObjectMapper().readValue(jsonResponse, CandidateDTO.class);

        assertNotNull(candidateDTORes);
        assertEquals("Renos", candidateDTORes.getName());
        assertEquals("Bardis", candidateDTORes.getLastName());
    }

    @Test
    void getAllCandidates_ShouldReturnListOfObjects_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        CandidateDTO candidateDTO1 = CandidateDTO.builder()
                .candidateId(id1).name("Renos").lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        final UUID id2 = UUID.randomUUID();
        CandidateDTO candidateDTO2 = CandidateDTO.builder()
                .candidateId(id2).name("Nikos").lastName("Papadopoulos").dob("15/10/1980").address("71 BD du President Wilson")
                .city("Nice").country("France").build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        List<CandidateDTO> candidateDTOList = new ArrayList<>(Arrays.asList(candidateDTO1, candidateDTO2));

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(candidateServiceImpl.getAllCandidates()).thenReturn(candidateDTOList);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/candidates")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(candidateDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(candidateDTOList.size())))
                .andExpect(jsonPath("$[0].candidateId", is(candidateDTOList.get(0).getCandidateId().toString())))
                .andExpect(jsonPath("$[0].name", is(candidateDTOList.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(candidateDTOList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].dob", is(candidateDTOList.get(0).getDob())))
                .andExpect(jsonPath("$[1].candidateId", is(candidateDTOList.get(1).getCandidateId().toString())))
                .andExpect(jsonPath("$[1].name", is(candidateDTOList.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(candidateDTOList.get(1).getLastName())))
                .andExpect(jsonPath("$[1].dob", is(candidateDTOList.get(1).getDob())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<CandidateDTO> myObjectsRes = om.readValue(jsonResponse, om.getTypeFactory().constructCollectionType(List.class, CandidateDTO.class));

        assertNotNull(myObjectsRes);
        assertEquals("Renos", myObjectsRes.get(0).getName());
        assertEquals("Bardis", myObjectsRes.get(0).getLastName());
        assertEquals("Nikos", myObjectsRes.get(1).getName());
        assertEquals("Papadopoulos", myObjectsRes.get(1).getLastName());

    }

    @Test
    void saveCandidate_ShouldReturnAnObject_ValidReturn() throws Exception {
        final UUID id = UUID.randomUUID();
        CandidateDTO candidateDTO = CandidateDTO.builder()
                .candidateId(id).name("Renos").lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(candidateServiceImpl.registerCandidate(any(CandidateDTO.class))).thenReturn(candidateDTO);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/candidate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(candidateDTO)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.candidateId", is(candidateDTO.getCandidateId().toString())))
                .andExpect(jsonPath("$.name", is(candidateDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(candidateDTO.getLastName())))
                .andExpect(jsonPath("$.dob", is(candidateDTO.getDob())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        CandidateDTO candidateDTORes = new ObjectMapper().readValue(jsonResponse, CandidateDTO.class);

        assertNotNull(candidateDTORes);
        assertEquals("Renos", candidateDTORes.getName());
        assertEquals("Bardis", candidateDTORes.getLastName());
    }

    @Test
    void getCandidatesByFirstName_ShouldReturnListOfObjects_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        CandidateDTO candidateDTO1 = CandidateDTO.builder()
                .candidateId(id1).name("Renos").lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        final UUID id2 = UUID.randomUUID();
        CandidateDTO candidateDTO2 = CandidateDTO.builder()
                .candidateId(id2).name("Renos").lastName("Papadopoulos").dob("15/10/1980").address("71 BD du President Wilson")
                .city("Nice").country("France").build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        List<CandidateDTO> candidateDTOList = new ArrayList<>(Arrays.asList(candidateDTO1, candidateDTO2));

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(candidateServiceImpl.getCandidateByName(any())).thenReturn(candidateDTOList);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/candidates/firstName/{firstName}", "Renos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(candidateDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(candidateDTOList.size())))
                .andExpect(jsonPath("$[0].candidateId", is(candidateDTOList.get(0).getCandidateId().toString())))
                .andExpect(jsonPath("$[0].name", is(candidateDTOList.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(candidateDTOList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].dob", is(candidateDTOList.get(0).getDob())))
                .andExpect(jsonPath("$[1].candidateId", is(candidateDTOList.get(1).getCandidateId().toString())))
                .andExpect(jsonPath("$[1].name", is(candidateDTOList.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(candidateDTOList.get(1).getLastName())))
                .andExpect(jsonPath("$[1].dob", is(candidateDTOList.get(1).getDob())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<CandidateDTO> myObjectsRes = om.readValue(jsonResponse, om.getTypeFactory().constructCollectionType(List.class, CandidateDTO.class));

        assertNotNull(myObjectsRes);
        assertEquals("Renos", myObjectsRes.get(0).getName());
        assertEquals("Bardis", myObjectsRes.get(0).getLastName());
        assertEquals("Renos", myObjectsRes.get(1).getName());
        assertEquals("Papadopoulos", myObjectsRes.get(1).getLastName());
    }

    @Test
    void getCandidatesByLastName_ShouldReturnListOfObjects_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        CandidateDTO candidateDTO1 = CandidateDTO.builder()
                .candidateId(id1).name("Renos").lastName("Bardis").dob("15/10/1987").address("78 BD du President Wilson")
                .city("Antibes").country("France").build();

        final UUID id2 = UUID.randomUUID();
        CandidateDTO candidateDTO2 = CandidateDTO.builder()
                .candidateId(id2).name("Nikos").lastName("Bardis").dob("15/10/1980").address("71 BD du President Wilson")
                .city("Nice").country("France").build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        List<CandidateDTO> candidateDTOList = new ArrayList<>(Arrays.asList(candidateDTO1, candidateDTO2));

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(candidateServiceImpl.getCandidatesByLastName(any())).thenReturn(candidateDTOList);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/candidates/lastName/{lastName}", "Bardis")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(candidateDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(candidateDTOList.size())))
                .andExpect(jsonPath("$[0].candidateId", is(candidateDTOList.get(0).getCandidateId().toString())))
                .andExpect(jsonPath("$[0].name", is(candidateDTOList.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(candidateDTOList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].dob", is(candidateDTOList.get(0).getDob())))
                .andExpect(jsonPath("$[1].candidateId", is(candidateDTOList.get(1).getCandidateId().toString())))
                .andExpect(jsonPath("$[1].name", is(candidateDTOList.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(candidateDTOList.get(1).getLastName())))
                .andExpect(jsonPath("$[1].dob", is(candidateDTOList.get(1).getDob())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<CandidateDTO> myObjectsRes = om.readValue(jsonResponse, om.getTypeFactory().constructCollectionType(List.class, CandidateDTO.class));

        assertNotNull(myObjectsRes);
        assertEquals("Renos", myObjectsRes.get(0).getName());
        assertEquals("Bardis", myObjectsRes.get(0).getLastName());
        assertEquals("Nikos", myObjectsRes.get(1).getName());
        assertEquals("Bardis", myObjectsRes.get(1).getLastName());
    }


}
