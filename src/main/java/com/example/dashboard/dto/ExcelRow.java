package com.example.dashboard.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class ExcelRow {

  private String key;
  private String keyName;
  private Map<String, String> values = new HashMap<>();

  public void setValues(Map<String, String> values) {
    this.values = values;
  }

  public Map<String, String> getValues() {
    return values;
  }

  public void put(String key, String value) {
    values.put(key, value);
  }

}
