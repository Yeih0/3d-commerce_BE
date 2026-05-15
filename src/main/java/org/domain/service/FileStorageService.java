package org.domain.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    //salvataggio file
    public String storeFile(MultipartFile file, String folder) throws BadRequestException {
        // Validazione file
        validateFile(file);

        // Genera nome file univoco
        String fileName = generateFileName(file.getOriginalFilename());

        try {
            // Crea folder se non esiste
            Path targetLocation = this.fileStorageLocation.resolve(folder);
            Files.createDirectories(targetLocation);

            // Copia file
            Path finalLocation = targetLocation.resolve(fileName);
            Files.copy(file.getInputStream(), finalLocation, StandardCopyOption.REPLACE_EXISTING);

            return folder + "/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    //caricamento file
    public Path loadFile(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }


    //cancellazione file
    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            return false;
        }
    }

    private void validateFile(MultipartFile file) throws BadRequestException {
        // Check se file vuoto
        if (file.isEmpty()) {
            throw new BadRequestException("File vuoto");
        }

        // Check dimensione (max 10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new BadRequestException("File troppo grande. Massimo 10MB");
        }

        // Check tipo file (solo immagini e modelli 3D)
        String contentType = file.getContentType();
        if (contentType == null || !isValidContentType(contentType)) {
            throw new BadRequestException("Tipo file non supportato. Usa: jpg, png, svg, stl");
        }

        // Check estensione
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new BadRequestException("Nome file non valido");
        }
    }

    private boolean isValidContentType(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/svg+xml") ||
                contentType.equals("image/webp") ||
                contentType.equals("application/sla") || // STL files
                contentType.equals("model/stl") ||
                contentType.equals("application/octet-stream"); // Generic binary
    }

    private String generateFileName(String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
}