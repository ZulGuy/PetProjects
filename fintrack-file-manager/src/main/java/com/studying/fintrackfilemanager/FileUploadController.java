package com.studying.fintrackfilemanager;

import com.studying.fintrackfilemanager.storage.StorageFileNotFoundException;
import com.studying.fintrackfilemanager.storage.StorageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

  StorageService storageService;

  @Autowired
  public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("file")MultipartFile file) {
    storageService.store(file);
    return "File uploaded successfully";
  }

  @GetMapping("/convert")
  public List<TransactionDTO> convertExcelToDB(@RequestParam("file") String fileLocation){
    TransactionsService transactionsService = new TransactionsService();
    List<TransactionDTO> transactions = transactionsService.excelDataToListOfObjets_withPOIJI(fileLocation);
    storageService.deleteAll();
    return transactions;
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}
