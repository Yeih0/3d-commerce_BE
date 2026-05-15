package org.domain.web;

import org.springframework.core.io.Resource;
import org.apache.coyote.BadRequestException;
import org.domain.dto.ApiResponse;
import org.domain.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Upload file (Admin only)
     */
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "products") String folder) throws BadRequestException {

        String fileName = fileStorageService.storeFile(file, folder);

        Map<String, String> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("url", "/api/files/" + fileName);
        response.put("size", String.valueOf(file.getSize()));

        return ResponseEntity.ok(ApiResponse.success("File caricato con successo", response));
    }

    /**
     * Download/View file (Public)
     */
    @GetMapping("/{folder}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String folder,
            @PathVariable String fileName) {

        try {
            Path filePath = fileStorageService.loadFile(folder + "/" + fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(fileName);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found: " + fileName, ex);
        }
    }

    /**
     * Delete file (Admin only)
     */
    @DeleteMapping("/{folder}/{fileName:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @PathVariable String folder,
            @PathVariable String fileName) {

        boolean deleted = fileStorageService.deleteFile(folder + "/" + fileName);

        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("File eliminato", null));
        } else {
            return ResponseEntity.ok(ApiResponse.error("File non trovato o non eliminato"));
        }
    }

    private String determineContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".svg")) {
            return "image/svg+xml";
        } else if (fileName.endsWith(".stl")) {
            return "model/stl";
        }
        return "application/octet-stream";
    }
}