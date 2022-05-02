package com.example.WeibisWeb.dao;

import com.example.WeibisWeb.resources.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository layer of File Uploaded
 */
@Repository
public interface FileUpLoadedRepository extends JpaRepository<UploadedFile, UUID> {

    /**
     * Retrieve Uploaded file by a given ID
     * @param id The id number of a Uploaded Image
     * @return A UploadedFile Entity
     */
    Optional<UploadedFile>  findByFileId(UUID id);
}
