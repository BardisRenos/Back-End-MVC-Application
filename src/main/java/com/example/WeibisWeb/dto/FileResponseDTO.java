package com.example.WeibisWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {

    private String fileId;
    private String fileType;
    private String message;
    private boolean upLoadStatus;
    private String downloadUri;

}
