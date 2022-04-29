package com.example.WeibisWeb.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The FileUploadedDTO class
 */
@Getter
@Setter
@RequiredArgsConstructor
public class FileUploadedDTO {

    private UUID fileId;
    private String fileName;
    private String fileType;
    private byte[] fileData;
}
