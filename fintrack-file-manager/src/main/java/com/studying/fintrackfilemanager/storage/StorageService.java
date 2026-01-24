package com.studying.fintrackfilemanager.storage;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  public void init();

  public void store(MultipartFile file);

  Path load(String filename);

  Resource loadAsResource(String filename);

  void deleteAll();

}
