package com.example.WeibisWeb.Controller;

import com.example.WeibisWeb.controller.JobDescriptionController;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dao.JobDescriptionRepository;
import com.example.WeibisWeb.dto.CandidateDTO;
import com.example.WeibisWeb.dto.ClientDTO;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
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

    @Test
    void getAllJobs_ShouldReturnListObject_ValidReturn() throws Exception {
        final UUID id1 = UUID.randomUUID();
        JobDescriptionDTO jobDescriptionDTO1 = JobDescriptionDTO.builder().jobDescriptionId(id1)
                .companyName("Atos").title("Java Developer").location("Lille").description("Searching for Java Developer").programmingLanguage("Java")
                .databaseName("SQL").framework("Spring").level("Junior").isOpen("Yes").numberNeeded(1).build();

        final UUID id2 = UUID.randomUUID();
        JobDescriptionDTO jobDescriptionDTO2 = JobDescriptionDTO.builder().jobDescriptionId(id2)
                .companyName("Alten").title("C++ Developer").location("Lille").description("Searching for Java Developer").programmingLanguage("Java")
                .databaseName("SQL").framework("No Framework").level("Junior").isOpen("Yes").numberNeeded(1).build();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        UserDetails userLogin = new org.springframework.security.core.userdetails.User("Renos87", "1234", authorityList);

        List<JobDescriptionDTO> JobDescriptionDTOList = new ArrayList<>(Arrays.asList(jobDescriptionDTO1, jobDescriptionDTO2));

        String tokenString = "0123456789.ABCDEFGJKLMN.!@#$%^";

        when(userService.loadUserByUsername("Renos87")).thenReturn(userLogin);
        when(jobDescriptionService.getAllJobs()).thenReturn(JobDescriptionDTOList);
        when(jwtUtil.generateToken(userLogin)).thenReturn("Bearer " + tokenString);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenString)
                .content(om.writeValueAsString(JobDescriptionDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(JobDescriptionDTOList.size())))
                .andExpect(jsonPath("$[0].jobDescriptionId", is(JobDescriptionDTOList.get(0).getJobDescriptionId().toString())))
                .andExpect(jsonPath("$[0].companyName", is(JobDescriptionDTOList.get(0).getCompanyName())))
                .andExpect(jsonPath("$[0].title", is(JobDescriptionDTOList.get(0).getTitle())))
                .andExpect(jsonPath("$[0].location", is(JobDescriptionDTOList.get(0).getLocation())))
                .andExpect(jsonPath("$[0].description", is(JobDescriptionDTOList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].programmingLanguage", is(JobDescriptionDTOList.get(0).getProgrammingLanguage())))
                .andExpect(jsonPath("$[0].databaseName", is(JobDescriptionDTOList.get(0).getDatabaseName())))
                .andExpect(jsonPath("$[0].framework", is(JobDescriptionDTOList.get(0).getFramework())))
                .andExpect(jsonPath("$[0].level", is(JobDescriptionDTOList.get(0).getLevel())))
                .andExpect(jsonPath("$[0].isOpen", is(JobDescriptionDTOList.get(0).getIsOpen())))
                .andExpect(jsonPath("$[0].numberNeeded", is(JobDescriptionDTOList.get(0).getNumberNeeded())))
                .andExpect(jsonPath("$[1].jobDescriptionId", is(JobDescriptionDTOList.get(1).getJobDescriptionId().toString())))
                .andExpect(jsonPath("$[1].companyName", is(JobDescriptionDTOList.get(1).getCompanyName())))
                .andExpect(jsonPath("$[1].title", is(JobDescriptionDTOList.get(1).getTitle())))
                .andExpect(jsonPath("$[1].location", is(JobDescriptionDTOList.get(1).getLocation())))
                .andExpect(jsonPath("$[1].description", is(JobDescriptionDTOList.get(1).getDescription())))
                .andExpect(jsonPath("$[1].programmingLanguage", is(JobDescriptionDTOList.get(1).getProgrammingLanguage())))
                .andExpect(jsonPath("$[1].databaseName", is(JobDescriptionDTOList.get(1).getDatabaseName())))
                .andExpect(jsonPath("$[1].framework", is(JobDescriptionDTOList.get(1).getFramework())))
                .andExpect(jsonPath("$[1].level", is(JobDescriptionDTOList.get(1).getLevel())))
                .andExpect(jsonPath("$[1].isOpen", is(JobDescriptionDTOList.get(1).getIsOpen())))
                .andExpect(jsonPath("$[1].numberNeeded", is(JobDescriptionDTOList.get(1).getNumberNeeded())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<JobDescriptionDTO> jobDescriptionDTORes = om.readValue(jsonResponse, om.getTypeFactory().constructCollectionType(List.class, JobDescriptionDTO.class));

        assertAll("Should return attributes of the JobDescriptionDTO object",
                ()->assertNotNull(jobDescriptionDTORes),
                ()->assertEquals("Atos", jobDescriptionDTORes.get(0).getCompanyName()),
                ()->assertEquals("Lille", jobDescriptionDTORes.get(0).getLocation()),
                ()->assertEquals("Spring", jobDescriptionDTORes.get(0).getFramework()),
                ()->assertEquals("Alten", jobDescriptionDTORes.get(1).getCompanyName()),
                ()->assertEquals("Lille", jobDescriptionDTORes.get(1).getLocation()),
                ()->assertEquals("No Framework", jobDescriptionDTORes.get(1).getFramework()));
    }
}
