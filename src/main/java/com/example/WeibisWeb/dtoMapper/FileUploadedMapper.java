package com.example.WeibisWeb.dtoMapper;

import com.example.WeibisWeb.dto.FileUploadedDTO;
import com.example.WeibisWeb.resources.UploadedFile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The dto mapper. The class that does the conversion from FileUploaded object into FileUploadedDTO object
 */
@Service
public class FileUploadedMapper {

    /**
     * The conversion of the fileUploadEntity object into FileUploadedDTO
     * @param fileUploadEntity A fileUploadEntity class
     * @return A FileUploadedDTO class
     */
    public static FileUploadedDTO convertAllFileUploadedEntityToDTO(UploadedFile fileUploadEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fileUploadEntity, FileUploadedDTO.class);
    }
}
