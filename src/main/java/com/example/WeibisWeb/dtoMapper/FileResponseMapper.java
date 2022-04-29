package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.FileResponseDTO;
import com.example.WeibisWeb.fileResponse.FileResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from Client object into ClientDTO object
 */
@Service
public class FileResponseMapper {

    public static FileResponseDTO convertAllFileResponseEntityToDTO(FileResponse fileResponseEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fileResponseEntity, FileResponseDTO.class);
    }
}
