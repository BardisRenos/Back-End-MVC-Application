package com.example.WeibisWeb.fileResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {

    private String fileId;
    private String fileType;
    private String message;
    private boolean upLoadStatus;
    private String downloadUri;

}
