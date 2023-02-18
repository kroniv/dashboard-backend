package com.example.dashboard.api;

import com.example.dashboard.service.ExcelService;
import com.example.dashboard.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static com.example.dashboard.Config.PATH_TO_SAVE_FILE;

@RestController
@RequestMapping("/excel")
public class ExcelController {

  @Autowired
  FileService fileService;
  @Autowired
  ExcelService excelService;

  @PostConstruct
  private void postConstruct() {
    File path = new File(PATH_TO_SAVE_FILE);
    if (!path.exists()) {
      path.mkdir();
    }
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
    fileService.saveFile(file);
    return ResponseEntity.ok("The file is uploaded successfully " + System.getProperty("user.dir"));
  }

  @CrossOrigin
  @PostMapping("/save")
  public ResponseEntity<String> saveExcel(@RequestParam("file") MultipartFile file) throws IOException {
    String fileName = fileService.saveFile(file);
    excelService.saveBudgets(fileName);
    return ResponseEntity.ok("The data is added successfully");
  }
}
