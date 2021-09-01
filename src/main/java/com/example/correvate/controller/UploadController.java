package com.example.correvate.controller;


import com.example.correvate.message.ResponseMessage;
import com.example.correvate.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.correvate.service.FilesStorageServiceImpl.zipoutputnow;

@Controller
@RequiredArgsConstructor
public class UploadController {

  private FilesStorageService storageService;


  @PostMapping(value = "/upload")
  public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
    String message;
    try {
      List<String> fileNames = new ArrayList<>();

      Arrays.stream(files).forEach(file -> {
        storageService.save(file);
        fileNames.add(file.getOriginalFilename());
      });

      message = "Uploaded the files successfully: " + fileNames;
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Fail to upload files!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/downloadzip")
  public void downloadFile(HttpServletResponse response) throws IOException {
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=uploads.zip");
    response.setStatus(HttpServletResponse.SC_OK);

    zipoutputnow(response.getOutputStream());
  }



}
