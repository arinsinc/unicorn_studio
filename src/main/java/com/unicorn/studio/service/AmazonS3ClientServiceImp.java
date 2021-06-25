package com.unicorn.studio.service;



import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.unicorn.studio.dao.MediaStorageRepository;
import com.unicorn.studio.entity.MediaStorage;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.projection.MediaStorageProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
public class AmazonS3ClientServiceImp implements AmazonS3ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ClientServiceImp.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private MediaStorageRepository mediaStorage;

    @Autowired
    private UserService userService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.bucket_url}")
    private String bucketUrl;

    /**
     *
     * @param multipartFile
     * @param fileType
     * @return fileName
     */
    @Override
    @Async
    public String uploadFile(MultipartFile multipartFile, String fileType) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            String fileName = uploadFileToS3Bucket(bucketName, file, fileType);
            LOGGER.info("File upload is completed.");
            file.delete();  // To remove the file locally created in the project folder.
            return fileName;
        } catch (final AmazonServiceException ex) {
            LOGGER.info("File upload is failed.");
            LOGGER.error("Error= {} while uploading file.", ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param multipartFile
     * @return file
     */
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }

    /**
     *
     * @param bucketName
     * @param file
     * @param fileType
     * @return uniqueFileName
     */
    private String uploadFileToS3Bucket(final String bucketName, final File file, String fileType) {
        final String uniqueFileName = Instant.now().toEpochMilli() + "_" + file.getName();
        String filePath = generateFilePath(fileType) + uniqueFileName;
        User user = userService.getUserIdByAuthentication();
        MediaStorage storage = getMediaDetail(fileType, user);
        if (storage == null) {
            storage = new MediaStorage(uniqueFileName,fileType);
            storage.setId(0);
            storage = setMediaParent(storage, fileType, user);
        }
        else {
            String fileUrl = bucketUrl + "/" + (fileType.equals("profile") ? "profile" : "company-logo") + "/"+ storage.getFileName();
            deleteFile(fileUrl, "update");
            storage.setFileName(uniqueFileName);
        }
        LOGGER.info("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file).withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(putObjectRequest);
        saveFileDetails(storage);
        return uniqueFileName;
    }

    /**
     *
     * @param fileName
     * @param method
     */
    @Override
    @Async
    public void deleteFile(String fileName, String method) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName,fileName));
            LOGGER.info("File deleted successfully= " + fileName);
            if (method != "update") {
                fileName = fileName.substring(fileName.lastIndexOf("/")+1);
                deleteFileDetails(fileName);
            }
        } catch (AmazonServiceException ex) {
            LOGGER.error("Error occurred= {} while deleting file", ex.getMessage());
        }
    }

    /**
     * @param fileType
     * @return filePath
     */
    private String generateFilePath(String fileType) {
        String filePath = "images/";
        switch (fileType) {
            case "profile_pic":
                filePath += "profile/";
            case "cover":
                filePath += "cover/";
            default:
                filePath += "company-logo/";
        }
        return filePath;
    }

    /**
     * @param fileType
     * @param user
     * @return mediaStorage
     */
    @Transactional
    private MediaStorage getMediaDetail(String fileType, User user) {
        MediaStorage mediaStorage = new MediaStorage();
        switch (fileType) {
            case "profile_pic":
                mediaStorage = user.getMedia();
            case "company_logo":
                mediaStorage = user.getCompany().getMedia().get(0);
            case "investor_logo":
                mediaStorage = user.getInvestor().getMedia().get(0);
            case "program_logo":
                mediaStorage = user.getStartupProgram().getMedia().get(0);
        }
        return mediaStorage;
    }

    /**
     * @param media
     * @param fileType
     * @param user
     * @return media
     */
    @Transactional
    private MediaStorage setMediaParent(MediaStorage media, String fileType, User user) {
        switch (fileType) {
            case "profile_pic":
                media.setUser(user);
            case "company_logo":
                media.setCompany(user.getCompany());
            case "investor_logo":
                media.setInvestor(user.getInvestor());
            case "program_logo":
                media.setStartupProgram(user.getStartupProgram());
        }
        return media;
    }

    /**
     *
     * @param fileDetails
     */
    @Transactional
    private void saveFileDetails(MediaStorage fileDetails) {
        mediaStorage.save(fileDetails);
    }

    /**
     *
     * @param fileName
     * @return MediaStorage
     */
    @Override
    @Transactional
    public MediaStorageProjection getFileDetails(String fileName) {
        return mediaStorage.findByFilename(fileName).get();
    }

    /**
     *
     * @param fileName
     */
    @Transactional
    private void deleteFileDetails(String fileName) {
        mediaStorage.deleteByFileName(fileName);
    }
}
