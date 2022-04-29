package com.example.WeibisWeb.Service;

import com.example.WeibisWeb.dao.UserRepository;
import com.example.WeibisWeb.dto.UserDTO;
import com.example.WeibisWeb.dto.UserNoPassDTO;
import com.example.WeibisWeb.exception.UserNotFoundException;
import com.example.WeibisWeb.resources.User;
import com.example.WeibisWeb.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        this.userRepository = mock(UserRepository.class);
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);
    }

    @Test
    void getAllUsers_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(passwordEncoder.encode("1234")).build();

        final UUID id2 = UUID.randomUUID();
        User user2 = User.builder().userId(id2).firstName("Nikos")
                .username("Nikos82").lastName("Papas")
                .email("nikos@gmail.com").role("USER").password(passwordEncoder.encode("1234")).build();

        List<User> userList = new ArrayList<>(Arrays.asList(user1, user2));
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDTO> userDTOList = userService.getAllUsers();

        assertAll("Should return attributes of the UserDTO object",
                ()->assertEquals("Renos", userDTOList.get(0).getFirstName()),
                ()->assertEquals("Bardis", userDTOList.get(0).getLastName()),
                ()->assertEquals("Nikos", userDTOList.get(1).getFirstName()),
                ()->assertEquals("Papas", userDTOList.get(1).getLastName()));
    }

    @Test
    void getUserById_ShouldReturnAnObject_ValidReturn() throws UserNotFoundException {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(passwordEncoder.encode("1234")).build();

        when(userRepository.findByUserId(id1)).thenReturn(Optional.ofNullable(user1));
        UserDTO userDTO = userService.getUserById(id1);

        assertAll("Should return attributes of the UserDTO object",
                ()->assertEquals("Renos", userDTO.getFirstName()),
                ()->assertEquals("Bardis", userDTO.getLastName()),
                ()->assertEquals("Renos87", userDTO.getUsername()),
                ()->assertEquals("ADMIN", userDTO.getRole()));
    }

    @Test
    void getUserByLastName_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(passwordEncoder.encode("1234")).build();

        final UUID id2 = UUID.randomUUID();
        User user2 = User.builder().userId(id2).firstName("Nikos")
                .username("Nikos89").lastName("Bardis")
                .email("nikos@gmail.com").role("USER").password(passwordEncoder.encode("1234")).build();

        List<User> userList = new ArrayList<>(Arrays.asList(user1, user2));
        when(userRepository.findByLastName("Bardis")).thenReturn(userList);
        List<UserDTO> userDTOList = userService.getUserByLastName("Bardis");

        assertAll("Should return attributes of the UserDTO object",
                ()->assertEquals("Renos", userDTOList.get(0).getFirstName()),
                ()->assertEquals("Bardis", userDTOList.get(0).getLastName()),
                ()->assertEquals("Nikos", userDTOList.get(1).getFirstName()),
                ()->assertEquals("Bardis", userDTOList.get(1).getLastName()));
    }

    @Test
    void getUserByFirstName_ShouldReturnAListOfObjects_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(passwordEncoder.encode("1234")).build();

        final UUID id2 = UUID.randomUUID();
        User user2 = User.builder().userId(id2).firstName("Renos")
                .username("Renos89").lastName("Papas")
                .email("renos89@gmail.com").role("USER").password(passwordEncoder.encode("1234")).build();

        List<User> userList = new ArrayList<>(Arrays.asList(user1, user2));

        when(userRepository.findByFirstName("Renos")).thenReturn(userList);
        List<UserDTO> userDTOList = userService.getUserByFirstName("Renos");

        assertAll("Should return attributes of the UserDTO object",
                ()->assertEquals("Renos", userDTOList.get(0).getFirstName()),
                ()->assertEquals("Bardis", userDTOList.get(0).getLastName()),
                ()->assertEquals("Renos", userDTOList.get(1).getFirstName()),
                ()->assertEquals("Papas", userDTOList.get(1).getLastName()));
    }

    @Test
    void getUserByEmail_ShouldReturnAnObject_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(passwordEncoder.encode("1234")).build();

        when(userRepository.findByEmail("renos@gmail.com")).thenReturn(Optional.ofNullable(user1));
        UserDTO userDTO = userService.getUserByEmail("renos@gmail.com");

        assertAll("Should return attributes of the UserDTO object",
                ()->assertEquals("Renos", userDTO.getFirstName()),
                ()->assertEquals("Bardis", userDTO.getLastName()),
                ()->assertEquals("Renos87", userDTO.getUsername()),
                ()->assertEquals("renos@gmail.com", userDTO.getEmail()));
    }

    @Test
    void registerUser_ShouldReturnAnObject_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis").email("renos@gmail.com").role("ADMIN")
                .password(passwordEncoder.encode("1234")).build();

        UserDTO userDTO1 = new UserDTO(id1, "Renos", "Renos87" , "Bardis", "renos@gmail.com", "ADMIN", passwordEncoder.encode("1234"));

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);
        UserNoPassDTO userDTO = userService.registerUser(userDTO1);

        assertAll("Should return attributes of the UserNoPassDTO object",
                ()->assertEquals("Renos", userDTO.getFirstName()),
                ()->assertEquals("Bardis", userDTO.getLastName()),
                ()->assertEquals("Renos87", userDTO.getUsername()),
                ()->assertEquals("renos@gmail.com", userDTO.getEmail()));
    }

    @Test
    void loadUserByUsername_ShouldReturnAnObject_ValidReturn() {
        final UUID id1 = UUID.randomUUID();
        User user1 = User.builder().userId(id1).firstName("Renos")
                .username("Renos87").lastName("Bardis")
                .email("renos@gmail.com").role("ADMIN").password(new BCryptPasswordEncoder().encode("1234")).build();

        when(userRepository.findByUsername("Renos87")).thenReturn(user1);
        UserDetails userDetails = userService.loadUserByUsername("Renos87");

        assertAll("Should return attributes of the UserNoPassDTO object",
                ()->assertEquals("Renos87", userDetails.getUsername()),
                ()->assertEquals("[USER_ADMIN]", userDetails.getAuthorities().toString()));
    }

}