package test.unicorn.studio.controller;

import com.unicorn.studio.controller.AmazonS3Controller;
import com.unicorn.studio.service.AmazonS3ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(AmazonS3Controller.class)
@WebMvcTest(AmazonS3Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class AmazonS3ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AmazonS3ClientService amazonS3;

    @Test
    public void shouldUploadImageReturns201() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "s3-logo.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Spring Boot!".getBytes()
        );
        mockMvc.perform(multipart("/api/v1/images")
                .file(file))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldDeleteImageReturns200() throws Exception {
        mockMvc.perform(delete("/api/v1/images")
                .param("url",""))
                .andExpect(status().isOk());
    }
}
