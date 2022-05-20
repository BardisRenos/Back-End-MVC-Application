package com.example.WeibisWeb.Controller;

import com.example.WeibisWeb.controller.JobDescriptionController;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dao.JobDescriptionRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.dto.JobDescriptionDTO;
import com.example.WeibisWeb.exception.JobDescriptionNotFoundException;
import com.example.WeibisWeb.resources.JobDescription;
import com.example.WeibisWeb.security.JwtAuthEntrypoint;
import com.example.WeibisWeb.security.JwtFilter;
import com.example.WeibisWeb.security.JwtUtil;
import com.example.WeibisWeb.service.JobDescriptionServiceImpl;
import com.example.WeibisWeb.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * JUnit testing for the CandidateController layer
 */
@WebMvcTest(controllers = JobDescriptionController.class)
@ActiveProfiles("test")
class JobDescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JobDescriptionServiceImpl jobDescriptionService;

    @MockBean
    private UserServiceImpl userService;

    @InjectMocks
    private JobDescriptionController jobDescriptionController;

    @MockBean
    private JobDescriptionRepository jobDescriptionRepository;

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
        this.jobDescriptionController = new JobDescriptionController(this.jobDescriptionService);
    }

    @Test
    void getJobsDescriptionById_ShouldReturnAnObject_ValidReturn() throws Exception {
        final UUID id = UUID.randomUUID();

        JobDescriptionDTO jobDescriptionDTO = JobDescriptionDTO.builder().jobDescriptionId(id)
                .companyName("Atos").title("Java Developer").location("Lille").description("Searching for Java Developer").programmingLanguage("Java")
                .databaseName("SQL").framework("Spring").level("Junior").isOpen("Yes").numberNeeded(1).build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(jobDescriptionService.getJobsDescriptionById(id)).thenReturn(jobDescriptionDTO);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/job?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenString)
                        .content(om.writeValueAsString(jobDescriptionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jobDescriptionId", is(jobDescriptionDTO.getJobDescriptionId().toString())))
                .andExpect(jsonPath("$.companyName", is(jobDescriptionDTO.getCompanyName())))
                .andExpect(jsonPath("$.title", is(jobDescriptionDTO.getTitle())))
                .andExpect(jsonPath("$.location", is(jobDescriptionDTO.getLocation())))
                .andExpect(jsonPath("$.description", is(jobDescriptionDTO.getDescription())))
                .andExpect(jsonPath("$.programmingLanguage", is(jobDescriptionDTO.getProgrammingLanguage())))
                .andExpect(jsonPath("$.databaseName", is(jobDescriptionDTO.getDatabaseName())))
                .andExpect(jsonPath("$.framework", is(jobDescriptionDTO.getFramework())))
                .andExpect(jsonPath("$.level", is(jobDescriptionDTO.getLevel())))
                .andExpect(jsonPath("$.isOpen", is(jobDescriptionDTO.getIsOpen())))
                .andExpect(jsonPath("$.numberNeeded", is(jobDescriptionDTO.getNumberNeeded())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JobDescriptionDTO jobDescriptionDTORes = new ObjectMapper().readValue(jsonResponse, JobDescriptionDTO.class);

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertNotNull(jobDescriptionDTORes),
                ()->assertEquals("Atos", jobDescriptionDTORes.getCompanyName()),
                ()->assertEquals("Lille", jobDescriptionDTORes.getLocation()),
                ()->assertEquals("Spring", jobDescriptionDTORes.getFramework()));
    }

}
