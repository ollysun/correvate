package com.example.correvate.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

  public void save(MultipartFile file);

  public void deleteAll();

}
