package com.example.dashboard.api;

import com.example.dashboard.entities.Region;
import com.example.dashboard.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
public class RegionController {

  @Autowired
  RegionService regionService;

  @GetMapping()
  public List<Region> getAllRegions() {
    return regionService.getAllRegions();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Region> getRegionById(@PathVariable String id) {
    if (id == null) {
      return new ResponseEntity<Region>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Region>(regionService.getRegionById(id), HttpStatus.OK);
  }
}
