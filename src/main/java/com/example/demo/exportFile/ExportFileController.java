package com.example.demo.exportFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExportFileController {
    @GetMapping("/export-file-v1")
    public ResponseEntity<byte[]> downloadFile(String fileName) throws IOException {
        List<Student> students = ExportExcelUtil.readExcel("", Student.class);
        Map<String, Object> data = new HashMap<>();
        data.put("students", students);
        data.put("date", now());
        byte[] response = ExportExcelUtil.exportExcelWithTemplate("",data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "ds sv.xlsx" + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(response.length));
        String headerValue = "inline; filename=\"" + "ds sv.xlsx" + "\"";
        return ResponseEntity.ok()
//                .contentType(MediaType.)  // Set the MIME type
                .headers(headers) // For download prompt
                .body(response); // Serve the file
    }

    private String now() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zonedDateTime.format(formatter);
    }
}
