package com.example.WeibisWeb.controller;

import com.example.WeibisWeb.dto.FileResponseDTO;
import com.example.WeibisWeb.dto.FileUploadedDTO;
import com.example.WeibisWeb.dtoMapper.FileResponseMapper;
import com.example.WeibisWeb.exception.FileUploadedNotFoundException;
import com.example.WeibisWeb.fileResponse.FileResponse;
import com.example.WeibisWeb.service.UploadedFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * The Controller layer of FileUpload
 */
@RestController
@RequestMapping("/api/1.0/")
@RequiredArgsConstructor
public class FileUploadController {

    private final UploadedFileServiceImpl uploadedFileServiceImpl;

    /**
     * Uploading a file the Database
     * @param file The MultipartFile object
     * @return A FileResponse object
     */
    @PostMapping(value = "/uploaded/db")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDTO uploadedTheFile(@RequestParam(value = "file") MultipartFile file) {

        FileUploadedDTO uploadedFile = uploadedFileServiceImpl.uploadedToDb(file);
        FileResponse fileResponse = new FileResponse();
        if (uploadedFile!=null) {
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/download/")
                    .path(uploadedFile.getFileId().toString())
                    .toUriString();
            fileResponse.setDownloadUri(downloadUri);
            fileResponse.setFileId(uploadedFile.getFileId().toString());
            fileResponse.setFileType(uploadedFile.getFileType());
            fileResponse.setUpLoadStatus(true);
            fileResponse.setMessage("The file uploaded successfully");

            return FileResponseMapper.convertAllFileResponseEntityToDTO(fileResponse);
        }
        fileResponse.setMessage("****ERROR****");
        return FileResponseMapper.convertAllFileResponseEntityToDTO(fileResponse);
    }

    /**
     * Uploading files the Database
     * @param files Array of MultipartFile object
     * @return A FileResponseDTO object
     */
    @PostMapping(value = "/uploadFiles")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDTO uploadedFiles(@RequestParam(value = "files") MultipartFile[] files) {
        ArrayList<FileUploadedDTO> res = (ArrayList<FileUploadedDTO>) Arrays.asList(files).stream().map(uploadedFileServiceImpl::uploadedToDb).collect(Collectors.toList());
        FileResponse fileResponse = new FileResponse();
        if (!res.isEmpty()) {
            fileResponse.setMessage(res.size()+" files have been uploaded successfully");
        }
        fileResponse.setMessage("****ERROR****");
        return FileResponseMapper.convertAllFileResponseEntityToDTO(fileResponse);
    }

    /**
     * Uploading files the Database
     * @param id The id of the file
     * @return A FileResponse object
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> downloadTheFile(@PathVariable UUID id) throws FileUploadedNotFoundException {
        FileUploadedDTO downloadedFile = uploadedFileServiceImpl.downloadFileService(id);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(downloadedFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+downloadedFile.getFileName())
                .body(new ByteArrayResource(downloadedFile.getFileData()));
    }

}
