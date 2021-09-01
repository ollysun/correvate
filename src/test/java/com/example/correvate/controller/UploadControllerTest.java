package com.example.correvate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.correvate.message.ResponseMessage;
import com.example.correvate.service.FilesStorageService;
import com.example.correvate.service.FilesStorageServiceImpl;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UploadControllerTest {
    @Test
    public void testUploadFiles() throws UnsupportedEncodingException {
        UploadController uploadController = new UploadController(new FilesStorageServiceImpl());
        assertThrows(RuntimeException.class, () -> uploadController
                .uploadFiles(new MultipartFile[]{new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))}));
    }

    @Test
    public void testUploadFilesOK() throws UnsupportedEncodingException {
        FilesStorageService filesStorageService = mock(FilesStorageService.class);
        doNothing().when(filesStorageService).save((MultipartFile) any());
        UploadController uploadController = new UploadController(filesStorageService);
        ResponseEntity<ResponseMessage> actualUploadFilesResult = uploadController
                .uploadFiles(new MultipartFile[]{new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))});
        assertTrue(actualUploadFilesResult.getHeaders().isEmpty());
        assertTrue(actualUploadFilesResult.hasBody());
        assertEquals(HttpStatus.OK, actualUploadFilesResult.getStatusCode());
        assertEquals("Uploaded the files successfully: []", actualUploadFilesResult.getBody().getMessage());
        verify(filesStorageService).save((MultipartFile) any());
    }

    @Test
    public void testUploadFiles3() {
        FilesStorageService filesStorageService = mock(FilesStorageService.class);
        doNothing().when(filesStorageService).save((MultipartFile) any());
        UploadController uploadController = new UploadController(filesStorageService);
        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("foo");
        ResponseEntity<ResponseMessage> actualUploadFilesResult = uploadController
                .uploadFiles(new MultipartFile[]{mockMultipartFile});
        assertTrue(actualUploadFilesResult.getHeaders().isEmpty());
        assertTrue(actualUploadFilesResult.hasBody());
        assertEquals(HttpStatus.OK, actualUploadFilesResult.getStatusCode());
        assertEquals("Uploaded the files successfully: [foo]", actualUploadFilesResult.getBody().getMessage());
        verify(filesStorageService).save((MultipartFile) any());
        verify(mockMultipartFile).getOriginalFilename();
    }

    @Test
    public void testUploadFiles4() {
        FilesStorageService filesStorageService = mock(FilesStorageService.class);
        doNothing().when(filesStorageService).save((MultipartFile) any());
        ResponseEntity<ResponseMessage> actualUploadFilesResult = (new UploadController(filesStorageService))
                .uploadFiles(new MultipartFile[]{});
        assertTrue(actualUploadFilesResult.getHeaders().isEmpty());
        assertTrue(actualUploadFilesResult.hasBody());
        assertEquals(HttpStatus.OK, actualUploadFilesResult.getStatusCode());
        assertEquals("Uploaded the files successfully: []", actualUploadFilesResult.getBody().getMessage());
    }

    @Test
    public void testUploadFiles5() throws UnsupportedEncodingException {
        FilesStorageService filesStorageService = mock(FilesStorageService.class);
        doNothing().when(filesStorageService).save((MultipartFile) any());
        UploadController uploadController = new UploadController(filesStorageService);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));

        ResponseEntity<ResponseMessage> actualUploadFilesResult = uploadController.uploadFiles(new MultipartFile[]{
                mockMultipartFile, new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))});
        assertTrue(actualUploadFilesResult.getHeaders().isEmpty());
        assertTrue(actualUploadFilesResult.hasBody());
        assertEquals(HttpStatus.OK, actualUploadFilesResult.getStatusCode());
        assertEquals("Uploaded the files successfully: [, ]", actualUploadFilesResult.getBody().getMessage());
        verify(filesStorageService, atLeast(1)).save((MultipartFile) any());
    }
}

