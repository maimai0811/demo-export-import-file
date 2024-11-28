package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.demo.dto.response.DownloadFileResponse;

@Service
public class FileService {

    private static final String FILE_DIRECTORY = "C:\\Users\\DELL\\VimoPJ\\Source\\demo1\\src\\main\\resources\\download\\";

    public void downloadFile(String filename, SseEmitter emitter, String clientId) {

        File file = new File(FILE_DIRECTORY + filename);
        DownloadFileResponse response = new DownloadFileResponse();

        if (!file.exists()) {
            try {
                response.setMessage("File not found");
                emitter.send(SseEmitter.event().name("error").data(response));
                emitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            long fileSize = fileChannel.size();
            long bytesRead = 0;

            // Đọc file theo từng phần nhỏ
            byte[] buffer = new byte[8192];
            int bytesReadInCurrentIteration;
            while ((bytesReadInCurrentIteration = fileInputStream.read(buffer)) != -1) {
                bytesRead += bytesReadInCurrentIteration;

                // Gửi thông tin tiến độ tải về client qua SSE
                double progress = ((double) bytesRead / fileSize) * 100;
                response.setProgress(progress);
                response.setMessage("Processing download file. Please wait...");
                emitter.send(SseEmitter.event().name("progress").data(response));

                // Tiếp tục đọc và gửi cho client
            }




            // Sau khi tải xong
            response.setProgress(100);
            response.setMessage("Download complete");
            response.setUrl("Link download");
            emitter.send(SseEmitter.event().name("complete").data(response));
            emitter.complete();
        } catch (IOException e) {
            try {
                emitter.send(SseEmitter.event().name("error").data("Error while downloading the file"));
                emitter.complete();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void downloadFile1(String filename, SseEmitter emitter, String clientId) throws IOException {
        File file = new File(FILE_DIRECTORY + filename);
        DownloadFileResponse response = new DownloadFileResponse();
        int totalCount = count();
        new Thread(() -> {
            try {
                if (totalCount == 0) {
                    response.setMessage("Data empty");
                    emitter.send(SseEmitter.event().name("error").data(response));
                    emitter.complete();
                }
                int chunkSize = 1000;
                int downloadPart = (int) Math.ceil((double) totalCount / chunkSize);

                for (int i = 0; i < downloadPart; i++) {
                    double progress = ((double) (chunkSize * i) / totalCount) * 100;
//                    double progress = downloadPart;
                    if (i % 2 == 0) {
                        Thread.sleep(30);
                    } else {
                        Thread.sleep(50);
                    }
                    response.setProgress(progress);
                    response.setMessage("Processing download file. Please wait...");
                    emitter.send(SseEmitter.event().name("progress").data(response));
                }
                response.setProgress(100);
                response.setMessage("Download complete");
                response.setUrl("http://localhost:8080/api/download1/test.txt");
                emitter.send(SseEmitter.event().name("complete").data(response));
                emitter.complete();
            } catch (Exception ex) {
                try {
                    emitter.send(SseEmitter.event().name("error").data("Error while downloading the file"));
                    emitter.complete();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }

    private int count() {
        return 300000;
    }

    public static <T> List<List<T>> splitList(List<T> originalList, int chunkSize) {
        return IntStream.range(0, (originalList.size() + chunkSize - 1) / chunkSize)
                .mapToObj(i -> originalList.subList(i * chunkSize, Math.min((i + 1) * chunkSize, originalList.size())))
                .collect(Collectors.toList());
    }
}
