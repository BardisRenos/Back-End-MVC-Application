package com.example.WeibisWeb.resources;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


/**
 * The entity class of the Uploaded Files database table
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "uploaded_files")
public class UploadedFile implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "file_id", updatable = false, nullable = false)
    private UUID fileId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

}
