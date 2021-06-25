package com.unicorn.studio.service;

import com.unicorn.studio.entity.MediaStorage;
import com.unicorn.studio.projection.MediaStorageProjection;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService {
    String uploadFile(MultipartFile multipartFile, String fileType);
    void deleteFile(String fileName, String method);
    MediaStorageProjection getFileDetails(String uid);
}
