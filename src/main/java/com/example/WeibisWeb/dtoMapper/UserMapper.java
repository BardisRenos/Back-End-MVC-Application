package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.UserDTO;
import com.example.WeibisWeb.dto.UserNoPassDTO;
import com.example.WeibisWeb.resources.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from User object into UserDTO object
 */
@Service
public class UserMapper {

    /**
     * The conversion of the User object into UserDTO
     * @param userEntity userEntity class
     * @return A UserDTO class
     */
    public static UserDTO convertAllUsersEntityToDTO(User userEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserDTO.class);
    }

    /**
     * The conversion of the UserDTO object into User object
     * @param userDTO UserDTO class
     * @return A User Class
     */
    public static User convertAllUsersDtoTOEntity(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * The conversion of the UserDTO into UserNoPassDTO
     * @param userEntity User entity class
     * @return UserNoPassDTO class
     */
    public static UserNoPassDTO convertUserEntityIntoDTO(User userEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserNoPassDTO.class);
    }

    /**
     *
     * @param userNoPassDTO
     * @return
     */
    public static User convertUserNoPassDTOToEntity(UserNoPassDTO userNoPassDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userNoPassDTO, User.class);
    }
}
