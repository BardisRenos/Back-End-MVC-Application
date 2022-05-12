package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.ClientJobsDTO;
import com.example.WeibisWeb.resources.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from Client object into ClientJobsDTO object
 */
@Service
public class ClientCandidateMapper {

    /**
     * The conversation of the Client object into ClientJobsDTO
     * @param clientEntity A clientEntity class
     * @return A ClientJobsDTO class
     */
    public static ClientJobsDTO convertAllClientEntityToDTO(Client clientEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clientEntity, ClientJobsDTO.class);
    }
}
