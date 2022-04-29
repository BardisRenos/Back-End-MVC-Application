package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dto.FileUploadedDTO;
import com.example.WeibisWeb.exception.FileUploadedNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * The UploadedFile Service Interface. All methods that UploadedFile service has.
 */
public interface UploadedFileService {

    FileUploadedDTO uploadedToDb(MultipartFile fileToUpload);
    FileUploadedDTO downloadFileService(UUID id) throws FileUploadedNotFoundException;
}
