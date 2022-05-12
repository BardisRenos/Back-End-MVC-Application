package com.example.WeibisWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO implements Serializable {

    private String fileId;
    private String fileType;
    private String message;
    private boolean upLoadStatus;
    private String downloadUri;

}
