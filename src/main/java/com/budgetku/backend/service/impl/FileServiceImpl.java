package com.budgetku.backend.service.impl;

import com.budgetku.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${local.storage.path}")
    private String storagePath;

    @Override
    public String putObject(MultipartFile file) throws IOException {
        String filePath = Paths.get(storagePath, file.getOriginalFilename()).toString();
        File destFile = new File(filePath);
        file.transferTo(destFile);
        return file.getOriginalFilename();
    }

    @Override
    public void deleteObject(String fileKey) {
        File file = new File(Paths.get(storagePath, fileKey).toString());
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("Failed to delete file: " + fileKey);
            }
        }
    }
}

