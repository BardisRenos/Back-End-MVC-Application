package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.ClientDTO;
import com.example.WeibisWeb.resources.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from Client object into ClientDTO object
 */
@Service
public class ClientMapper {

    /**
     * The conversion of the Client object into ClientDTO
     * @param clientEntity clientEntity class
     * @return A ClientDTO class
     */
    public static ClientDTO convertAllClientEntityToDTO(Client clientEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clientEntity, ClientDTO.class);
    }

}
