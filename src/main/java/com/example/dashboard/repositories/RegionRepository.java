package com.example.dashboard.repositories;

import com.example.dashboard.entities.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, String> {

  List<Region> findByName(String str);
}
