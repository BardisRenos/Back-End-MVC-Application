package com.example.WeibisWeb.service;

import com.example.WeibisWeb.dao.FileUpLoadedRepository;
import com.example.WeibisWeb.dto.FileUploadedDTO;
import com.example.WeibisWeb.dtoMapper.FileUploadedMapper;
import com.example.WeibisWeb.exception.FileUploadedNotFoundException;
import com.example.WeibisWeb.resources.UploadedFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

/**
 * Service layer of UploadedFileService
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UploadedFileServiceImpl implements UploadedFileService {

    private final FileUpLoadedRepository fileUpLoadedRepository;

    /**
     * Upload the file to the Database
     * @param fileToUpload The file
     * @return FileUploadedDTO class
     */
    @Override
    public FileUploadedDTO uploadedToDb(MultipartFile fileToUpload) {
        UploadedFile uploadedFile = new UploadedFile();

        try {
            uploadedFile.setFileData(fileToUpload.getBytes());
            uploadedFile.setFileType(fileToUpload.getContentType());
            uploadedFile.setFileName(fileToUpload.getOriginalFilename());

            uploadedFile = fileUpLoadedRepository.save(uploadedFile);
            return FileUploadedMapper.convertAllFileUploadedEntityToDTO(uploadedFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve file by an Id
     * @param id The id of the
     * @return A UploadedFile class
     */
    @Override
    public FileUploadedDTO downloadFileService(UUID id) throws FileUploadedNotFoundException {
        return fileUpLoadedRepository.findByFileId(id).map(FileUploadedMapper::convertAllFileUploadedEntityToDTO).orElseThrow(()-> new FileUploadedNotFoundException(String.format("The File was not found with ID: %s", id)));
    }
}
