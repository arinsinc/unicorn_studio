package test.unicorn.studio.service;

import com.amazonaws.services.s3.AmazonS3;
import com.unicorn.studio.dao.MediaStorageRepository;
import com.unicorn.studio.entity.MediaStorage;
import com.unicorn.studio.service.AmazonS3ClientServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AmazonS3ServiceTest {
    @Mock
    private MediaStorageRepository storage;

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private AmazonS3ClientServiceImp amazonS3Client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    public void shouldSaveImageToS3() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile_pic.png",
                MediaType.IMAGE_PNG_VALUE,
                "Spring Boot!".getBytes()
        );
        amazonS3Client.uploadFile(file, "profile_pic");
        MediaStorage mediaStorage = new MediaStorage("profile_pic.png","profile_pic");
        assertEquals(mediaStorage, amazonS3Client.getFileDetails(mediaStorage.getFileName()));
    }

    @Test
    public void shouldDeleteImageFromS3() {}
}
