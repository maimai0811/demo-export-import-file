package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.demo.service.FileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DownloadController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download/{filename}")
    public @ResponseBody SseEmitter downloadFile(@PathVariable String filename, @RequestParam("clientId") String clientId) throws IOException {
        // Tạo emitter cho SSE
        SseEmitter emitter = new SseEmitter();
        fileService.downloadFile1(filename, emitter, clientId);  // Gọi service để tải file

        // Trả về emitter cho client
        return emitter;
    }

    private static final String FILE_DIRECTORY = "C:\\Users\\DELL\\VimoPJ\\Source\\demo1\\src\\main\\resources\\download\\";
    @GetMapping("/download1/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        // Path to the file on the server
        Path filePath = Paths.get(FILE_DIRECTORY, fileName).toAbsolutePath();

        // Check if the file exists
        if (!Files.exists(filePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if file doesn't exist
        }

        // Create Resource (use FileSystemResource to return file content as resource)
        Resource resource = new FileSystemResource(filePath);

        // Get the content type (Mime Type) of the file
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream"; // Default to binary stream if MIME type cannot be determined
        }

        // Set Content-Disposition header for file download
//        String headerValue = "attachment; filename=\"" + fileName + "\"";
        String headerValue = "inline; filename=\"" + fileName + "\"";

        // Return ResponseEntity with headers and file content
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))  // Set the MIME type
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue) // For download prompt
                .body(resource); // Serve the file
    }
}
