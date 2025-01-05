package com.budgetku.backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Base64DecodedMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String contentType;

    public Base64DecodedMultipartFile(byte[] content, String name, String contentType) {
        this.content = content;
        this.name = name;
        this.contentType = contentType != null ? contentType : "application/octet-stream";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("This operation is not supported for Base64DecodedMultipartFile.");
    }
}
