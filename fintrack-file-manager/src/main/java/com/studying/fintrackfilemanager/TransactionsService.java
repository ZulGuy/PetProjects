package com.studying.fintrackfilemanager;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.studying.fintrackfilemanager.storage.FileSystemStorageService;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

  private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  public List<TransactionDTO> excelDataToListOfObjets_withPOIJI(String fileLocation){
    PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
        .headerStart(23)
        .build();
    List<TransactionRow> rows = Poiji.fromExcel(new File(fileLocation), TransactionRow.class, options);
    for (TransactionRow row : rows) {
      if(row.getBookedAt() == null || row.getAmountDecimal() == 0) {
        int index = rows.indexOf(row);
        rows = rows.subList(0,index);
        break;
      }
    }
    return rows.stream()
        .filter(r -> r.getBookedAt() != null && !r.getBookedAt().trim().isEmpty())
        .filter(r -> Math.abs(r.getAmountDecimal()) > 1e-9)
        .map(r -> {
          String s = r.getBookedAt().trim();
          LocalDate d = LocalDate.parse(s, DMY);
          TransactionDTO dto = new TransactionDTO();
          dto.setBookedAt(java.sql.Timestamp.valueOf(d.atStartOfDay()));
          dto.setAmountDecimal(r.getAmountDecimal());
          dto.setCurrency(r.getCurrency());
          return dto;
        })
        .toList();
  }
}
