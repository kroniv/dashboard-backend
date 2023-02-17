package com.example.dashboard.service;

import com.example.dashboard.entities.Region;
import com.example.dashboard.repositories.RegionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

  @Autowired
  RegionRepository regionRepository;

  public void addRegion(String name) {
    Region region = new Region();
    region.setName(name);
    if (regionRepository.findByName(name).isEmpty()) {
      regionRepository.save(region);
    } else {
      //If founded
    }
  }

  public List<Region> getAllRegions() {
    return regionRepository.findAll();
  }

  public Region getRegionById(String id) {
    return regionRepository.findById(id).get();
  }
}
