package com.budgetku.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String putObject(MultipartFile file) throws IOException;

    void deleteObject(String fileKey);
}
