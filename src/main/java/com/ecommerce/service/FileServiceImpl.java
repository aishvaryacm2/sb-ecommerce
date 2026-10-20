package com.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomUuid = UUID.randomUUID().toString();
        String fileName = randomUuid.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        Path folder = Paths.get(path);
        Files.createDirectories(folder);
        Path filePath = folder.resolve(fileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath);
        }
        return fileName;
    }
}
