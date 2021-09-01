package com.example.correvate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.correvate.message.ResponseMessage;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UploadControllerTest {
    @Test
    public void testUploadFiles() throws UnsupportedEncodingException {
        UploadController uploadController = new UploadController();
        ResponseEntity<ResponseMessage> actualUploadFilesResult = uploadController
                .uploadFiles(new MultipartFile[]{new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))});
        assertTrue(actualUploadFilesResult.getHeaders().isEmpty());
        assertTrue(actualUploadFilesResult.hasBody());
        assertEquals(HttpStatus.EXPECTATION_FAILED, actualUploadFilesResult.getStatusCode());
        assertEquals("Fail to upload files!", actualUploadFilesResult.getBody().getMessage());
    }
}

