package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadFileResponse {
    private double progress;
    private String url;
    private String message;
}
