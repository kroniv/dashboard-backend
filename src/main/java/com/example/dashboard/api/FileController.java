package com.example.dashboard.api;

import com.example.dashboard.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

  @Autowired
  FileService fileService;

  @GetMapping("/{fileName}")
  public ResponseEntity<String> getFile(@PathVariable String fileName) throws IOException {
    return ResponseEntity.ok(fileService.getSerializedFile(fileName));
  }
}
