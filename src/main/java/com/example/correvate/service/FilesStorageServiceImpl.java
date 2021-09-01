package com.example.correvate.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private final Path root = Paths.get("src/main/resources/uploads");

  @Override
  public void save(MultipartFile file) {

    try {
      Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {

    try {
      // delete directory recursively
      FileUtils.cleanDirectory(root.toFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  public static void zipoutputnow(ServletOutputStream responseOutputStream) throws IOException {
    final String sourceFile = "src/main/resources/uploads";
    final ZipOutputStream zipOut;
    try (FileOutputStream fos = new FileOutputStream("src/main/resources/uploads.zip")) {
      zipOut = new ZipOutputStream(responseOutputStream);

      final File fileToZip = new File(sourceFile);

      zipFile(fileToZip, fileToZip.getName(), zipOut);
      zipOut.close();
      fos.close();
    }

  }

  private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
    if (fileToZip.isHidden()) {
      return;
    }
    if (fileToZip.isDirectory()) {
      if (fileName.endsWith("/")) {
        zipOut.putNextEntry(new ZipEntry(fileName));
        zipOut.closeEntry();
      } else {
        zipOut.putNextEntry(new ZipEntry(fileName + "/"));
        zipOut.closeEntry();
      }
      File[] children = fileToZip.listFiles();
      for (File childFile : children) {
        zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
      }
      return;
    }
    try (FileInputStream fis = new FileInputStream(fileToZip)) {
      ZipEntry zipEntry = new ZipEntry(fileName);
      zipOut.putNextEntry(zipEntry);
      byte[] bytes = new byte[1024];
      int length;
      while ((length = fis.read(bytes)) >= 0) {
        zipOut.write(bytes, 0, length);
      }
      //fis.close();
    }
  }

}
