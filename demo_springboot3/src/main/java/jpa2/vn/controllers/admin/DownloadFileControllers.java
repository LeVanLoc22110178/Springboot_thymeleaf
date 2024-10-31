package jpa2.vn.controllers.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpa2.vn.utils.Constant;

@RestController
public class DownloadFileControllers {
    
    @GetMapping("/image")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fname") String fileName) {
        File file = new File(Constant.UPLOAD_DIRECTORY + "/" + fileName);
        
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            InputStreamResource resource = new InputStreamResource(fileInputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
            MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(file.toPath()));
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
