package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.UserRepository;
import com.example.WeibisWeb.dto.UserDTO;
import com.example.WeibisWeb.dto.UserNoPassDTO;
import com.example.WeibisWeb.dtoMapper.UserMapper;
import com.example.WeibisWeb.exception.UserNotFoundException;
import com.example.WeibisWeb.resources.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The Service layer of the User
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Users"})
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieve all Users
     * @return A list of UserDTO
     */
    @Override
    @Cacheable(value = "Users")
    public List<UserDTO> getAllUsers() {
        log.info("The method is called");
        return userRepository.findAll().stream().map(UserMapper::convertAllUsersEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve the User entity by a given ID
     * @param id The id number of a User
     * @return A UserDTO Entity
     */
    @Override
    @Cacheable(value = "Users", key = "'UsersCache'+#id")
    public UserDTO getUserById(UUID id) throws UserNotFoundException {
        return userRepository.findByUserId(id).map(UserMapper::convertAllUsersEntityToDTO).orElseThrow(()->new UserNotFoundException(String.format("The User was not found with ID: %s", id)));
    }

    /**
     * Retrieve the User entity by a given Last name
     * @param lastName The lastName of a User
     * @return A list of UserNoPasswordDTO Entity
     */
    @Override
    public List<UserDTO> getUserByLastName(String lastName) {
        return userRepository.findByLastName(lastName).stream().map(UserMapper::convertAllUsersEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieve the User entity by a given first name
     * @param firstName The firstName of a User
     * @return A list of User Entity
     */
    @Override
    public List<UserDTO> getUserByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).stream().map(UserMapper::convertAllUsersEntityToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::convertAllUsersEntityToDTO).orElseThrow(()-> new UsernameNotFoundException(String.format("The User was not found with the email: %s", email)));
    }

    /**
     * Insert a new User entity
     * @param userDTO the UserDTO object
     * @return A UserDTO object
     */
    @Override
//    @CacheEvict(value = "users", allEntries = true)
    public UserNoPassDTO registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = UserMapper.convertAllUsersDtoTOEntity(userDTO);
        user = userRepository.save(user);
        return UserMapper.convertUserEntityIntoDTO(user);
    }

    /**
     * Retrieve users by a given username
     * @param username the username of the User Entity
     * @return A UserDetails Object
     * @throws UsernameNotFoundException Throws this exception in case
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException(String.format("User %s not found in the database", username));
        } else {
            log.info("User found in the database: {}", username);
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ADMIN"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
    }

}
