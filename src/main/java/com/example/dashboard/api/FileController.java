package com.example.dashboard.api;

import com.example.dashboard.service.FileService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

  @Autowired
  FileService fileService;

  @GetMapping("/{fileName}")
  public String getFile(@PathVariable String fileName) throws IOException {
    return fileService.getSerializedFile(fileName);
  }
}
